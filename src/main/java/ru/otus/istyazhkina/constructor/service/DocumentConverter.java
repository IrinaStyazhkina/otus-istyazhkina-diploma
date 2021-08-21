package ru.otus.istyazhkina.constructor.service;

import java.io.File;
import java.util.HashMap;

public interface DocumentConverter {

    String parseTemplate(String template, HashMap<String, Object> values);

    File generatePdf(String prefix, String html);

}
