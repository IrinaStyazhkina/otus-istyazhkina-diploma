package ru.otus.istyazhkina.constructor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.repository.DocumentTypesRepository;
import ru.otus.istyazhkina.constructor.service.TemplateResolver;

@Service
@RequiredArgsConstructor
public class TemplateResolverImpl implements TemplateResolver {

    private final DocumentTypesRepository documentTypesRepository;

    @Override
    public DocumentType resolveTemplate(String templateId) throws DataNotFoundException {
        return documentTypesRepository.findById(templateId).orElseThrow(() -> new DataNotFoundException("No template found by provided id!"));
    }
}
