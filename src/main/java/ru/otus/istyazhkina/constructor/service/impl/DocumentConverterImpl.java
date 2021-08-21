package ru.otus.istyazhkina.constructor.service.impl;

import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ru.otus.istyazhkina.constructor.service.DocumentConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class DocumentConverterImpl implements DocumentConverter {

    @Override
    public String parseTemplate(String template, HashMap<String, Object> values) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariables(values);

        return templateEngine.process(template, context);
    }

    @SneakyThrows
    @Override
    public File generatePdf(String prefix, String html) {
        File file = File.createTempFile(prefix, ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("public/fonts/Inter-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }
}
