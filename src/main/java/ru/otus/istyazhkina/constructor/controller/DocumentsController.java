package ru.otus.istyazhkina.constructor.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.istyazhkina.constructor.domain.entity.Doc;
import ru.otus.istyazhkina.constructor.domain.rest.DocDTO;
import ru.otus.istyazhkina.constructor.domain.rest.DocumentTypeDTO;
import ru.otus.istyazhkina.constructor.domain.rest.FormData;
import ru.otus.istyazhkina.constructor.domain.rest.PowerDTO;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.service.DocumentService;
import ru.otus.istyazhkina.constructor.service.factory.FormDataConverterFactoryService;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class DocumentsController {

    private final DocumentService documentService;
    private final FormDataConverterFactoryService formDataConverterFactory;

    @GetMapping("/api/template")
    @ResponseStatus(OK)
    public List<DocumentTypeDTO> getAllTemplates() {
        return documentService.getAllDocumentTypes()
                .stream()
                .map(DocumentTypeDTO::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/powers")
    @ResponseStatus(OK)
    public List<PowerDTO> getAllPowers() {
        return documentService.getAllPowers()
                .stream()
                .map(PowerDTO::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/doc/create/{templateId}")
    public ResponseEntity<HashMap<String, String>> createDocument(@PathVariable("templateId") String templateId, @RequestBody FormData formData) {
        File file = documentService.createDocument(templateId, formDataConverterFactory.getConverterByTemplateId(templateId).convertFormDataToMap(formData));
        String absolutePath = file.getAbsolutePath();
        String path = absolutePath.substring(16);
        HashMap<String, String> body = new HashMap<>();
        body.put("link", path);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/save")
    @ResponseStatus(CREATED)
    public void saveDocument(@RequestBody Map<String, String> payload) {
        documentService.save(payload.get("link"));
    }

    @GetMapping("/api/documents")
    public List<DocDTO> getAllDocuments() {
        List<Doc> allDocuments = documentService.getAllDocuments();
        allDocuments.forEach(doc -> doc.setFileName(doc.getFileName().substring(doc.getFileName().lastIndexOf("T") + 2)));
        return allDocuments
                .stream()
                .map(DocDTO::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/download-pdf/{tempFolder}/T/{doc}")
    public void downloadTemporaryPDFResource(@PathVariable("tempFolder") String tempFolder, @PathVariable("doc") String filePath, HttpServletResponse response) throws IOException {
        String file = "/var/folders/b8/" + tempFolder + "/T/" + filePath;
        Path path = Paths.get(file);
        if (Files.exists(path)) {
            prepareDownloadResponse(response, file, new BufferedInputStream(new FileInputStream(file)));
        }
    }

    @GetMapping("/document/{id}")
    public void downloadPdf(@PathVariable("id") String docId, HttpServletResponse response) throws DataNotFoundException, IOException {
        Doc documentById = documentService.getDocumentById(docId);
        prepareDownloadResponse(response, documentById.getFileName(), new ByteArrayInputStream(documentById.getFile().getData()));
    }

    private void prepareDownloadResponse(HttpServletResponse response, String filename, InputStream is) throws IOException {
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition",
                "attachment; filename=" + filename);
        IOUtils.copy(is, response.getOutputStream());
        response.getOutputStream().flush();
    }
}



