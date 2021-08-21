package ru.otus.istyazhkina.constructor.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.istyazhkina.constructor.domain.entity.Doc;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.domain.entity.Power;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.repository.DocRepository;
import ru.otus.istyazhkina.constructor.repository.DocumentTypesRepository;
import ru.otus.istyazhkina.constructor.repository.PowerRepository;
import ru.otus.istyazhkina.constructor.service.DocumentConverter;
import ru.otus.istyazhkina.constructor.service.DocumentService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentTypesRepository documentTypesRepository;
    private final DocumentConverter documentConverter;
    private final PowerRepository powerRepository;
    private final DocRepository docRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypesRepository.findAll();
    }

    @Override
    @SneakyThrows
    public File createDocument(String templateId, HashMap<String, Object> data) {
        DocumentType template = documentTypesRepository.findById(templateId).orElseThrow(() -> new DataNotFoundException("No template found by provided template id!"));
        String html = documentConverter.parseTemplate("templates/" + template.getLink(), data);
        return documentConverter.generatePdf(template.getAlias(), html);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Power> getAllPowers() {
        return powerRepository.findAll();
    }

    @Override
    @SneakyThrows
    @Transactional
    public void save(String link) {
        Path path = Paths.get("/var/folders/b8/" + link);
        Binary binary = new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(path));
        Doc document = Doc.builder()
                .fileName(link)
                .file(binary)
                .build();
        Doc insert = docRepository.insert(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doc> getAllDocuments() {
        return docRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Doc getDocumentById(String id) throws DataNotFoundException {
        return docRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Document by provided id is not found!"));
    }
}
