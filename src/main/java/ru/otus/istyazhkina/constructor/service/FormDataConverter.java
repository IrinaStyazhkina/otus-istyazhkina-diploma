package ru.otus.istyazhkina.constructor.service;

import ru.otus.istyazhkina.constructor.domain.rest.FormData;

import java.util.HashMap;

public interface FormDataConverter {

    HashMap<String, Object> convertFormDataToMap(FormData formData);

    String getTemplateId();
}
