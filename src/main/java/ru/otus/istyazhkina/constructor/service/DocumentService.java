package ru.otus.istyazhkina.constructor.service;

import ru.otus.istyazhkina.constructor.domain.entity.Doc;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.domain.entity.Power;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public interface DocumentService {

    List<DocumentType> getAllDocumentTypes();

    File createDocument(String templateId, HashMap<String, Object> data);

    List<Power> getAllPowers();

    void save(String link);

    List<Doc> getAllDocuments();

    Doc getDocumentById(String id) throws DataNotFoundException;
}
