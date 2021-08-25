package ru.otus.istyazhkina.constructor.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.istyazhkina.constructor.service.FormDataConverter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FormDataConverterFactoryService {
    private final Map<String, FormDataConverter> convertersByTemplateID;

    @Autowired
    public FormDataConverterFactoryService(List<FormDataConverter> converters) {
        convertersByTemplateID = converters.stream()
                .collect(Collectors.toMap(FormDataConverter::getTemplateId, Function.identity()));
    }

    public FormDataConverter getConverterByTemplateId(String templateId) {
        return convertersByTemplateID.get(templateId);
    }
}
