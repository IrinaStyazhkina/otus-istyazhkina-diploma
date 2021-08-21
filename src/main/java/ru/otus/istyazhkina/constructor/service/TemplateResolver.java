package ru.otus.istyazhkina.constructor.service;

import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;

public interface TemplateResolver {

    DocumentType resolveTemplate(String templateId) throws DataNotFoundException;

}
