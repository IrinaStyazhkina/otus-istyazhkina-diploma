package ru.otus.istyazhkina.constructor.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.istyazhkina.constructor.service.DocumentConverter;

import java.io.File;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DocumentConverterImplTest {

    @Autowired
    private DocumentConverter documentConverter;

    private static final String htmlDoc = "<html lang=\"ru\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\"/>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"font-family: 'Inter', serif\" class=\"page\">\n" +
            "\n" +
            "    <main class=\"content\">\n" +
            "        <h1>Тестовый <span>Документ</span></h1>\n" +
            "\n" +
            "    </main>\n" +
            "\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    @Test
    void shouldParseTemplateCorrectly() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("document", "Документ");
        String doc = documentConverter.parseTemplate("templates/test", data);
        assertThat(doc).isEqualTo(htmlDoc);
    }

    @Test
    void shouldCreateFile() {
        File file = documentConverter.generatePdf("Тестовый документ", htmlDoc);
        assertThat(file).exists().canRead();
        assertThat(file.getName()).contains("Тестовый документ");
    }

}