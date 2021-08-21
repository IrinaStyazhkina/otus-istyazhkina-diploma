package ru.otus.istyazhkina.constructor.controller;

import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ru.otus.istyazhkina.constructor.service.FormDataConverter;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class DocumentsController {

    private final DocumentService documentService;
    private final FormDataConverter orderDataConverter;
    private final FormDataConverter powerOfAttorneyDataConverter;

    public DocumentsController(DocumentService documentService,
                               @Qualifier("OrderToRaiseWagesDataConverter") FormDataConverter orderDataConverter,
                               @Qualifier("PowerOfAttorneyDataConverter") FormDataConverter powerOfAttorneyDataConverter) {
        this.documentService = documentService;
        this.orderDataConverter = orderDataConverter;
        this.powerOfAttorneyDataConverter = powerOfAttorneyDataConverter;

    }

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
        File file = null;
        if (templateId.equals("1"))
            file = documentService.createDocument(templateId, powerOfAttorneyDataConverter.convertFormDataToMap(formData));
        if (templateId.equals("2"))
            file = documentService.createDocument(templateId, orderDataConverter.convertFormDataToMap(formData));
        String absolutePath = file.getAbsolutePath();
        String path = absolutePath.substring(16);
        HashMap<String, String> body = new HashMap<>();
        body.put("link", path);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/download-pdf/{tempFolder}/T/{doc}")
    public void downloadTempPDFResource(@PathVariable("tempFolder") String tempFolder, @PathVariable("doc") String filePath, HttpServletResponse response) {
        Path path = Paths.get("/var/folders/b8/" + tempFolder + "/T/" + filePath);
        try {
            if (Files.exists(path)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + path.getFileName());
                Files.copy(path, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @GetMapping("/document/{id}")
    public void downloadPdf(@PathVariable("id") String docId, HttpServletResponse response) throws DataNotFoundException {
        Doc documentById = documentService.getDocumentById(docId);
        try {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition",
                    "attachment; filename=" + documentById.getFileName());
            IOUtils.copy(new ByteArrayInputStream(documentById.getFile().getData()), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @PostMapping("/save")
    @SneakyThrows
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


}



