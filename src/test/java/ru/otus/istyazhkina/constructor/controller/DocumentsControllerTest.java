package ru.otus.istyazhkina.constructor.controller;

import lombok.SneakyThrows;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.istyazhkina.constructor.advice.AppExceptionHandler;
import ru.otus.istyazhkina.constructor.domain.entity.Doc;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.domain.entity.Power;
import ru.otus.istyazhkina.constructor.security.SecurityConfiguration;
import ru.otus.istyazhkina.constructor.service.DocumentService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DocumentsController.class)
@Import({DocumentsController.class, AppExceptionHandler.class})
@ContextConfiguration(classes = {SecurityConfiguration.class, ControllerTestsConfiguration.class})
class DocumentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentService documentService;

    private final DocumentType documentType = DocumentType.builder()
            .id("1")
            .alias("Доверенность")
            .link("docs/powerOfAttorney")
            .build();

    private final Power power = Power.builder()
            .id("1")
            .name("Представительство")
            .value("Представлять интересы")
            .build();

    private final Doc doc = Doc.builder()
            .id("1")
            .file(new Binary(new byte[]{0, 1}))
            .fileName("T/file")
            .build();

    private static final String templatesJson = "[{\"id\":\"1\",\"alias\":\"Доверенность\", \"link\":\"docs/powerOfAttorney\"}]";
    private static final String powersJson = "[{\"id\":\"1\",\"name\":\"Представительство\", \"value\":\"Представлять интересы\"}]";
    private static final String docsJson = "[{\"id\":\"1\",\"fileName\":\"file\",\"file\":{\"type\":0,\"data\":\"AAE=\"}}]";


    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnAllTemplates() {
        Mockito.when(documentService.getAllDocumentTypes()).thenReturn(List.of(documentType));
        mockMvc.perform(get("/api/template"))
                .andExpect(status().is(200))
                .andExpect(content().json(templatesJson));
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnAllPowers() {
        Mockito.when(documentService.getAllPowers()).thenReturn(List.of(power));
        mockMvc.perform(get("/api/powers"))
                .andExpect(status().is(200))
                .andExpect(content().json(powersJson));
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnAllDocuments() {
        Mockito.when(documentService.getAllDocuments()).thenReturn(List.of(doc));
        mockMvc.perform(get("/api/documents"))
                .andExpect(status().is(200))
                .andExpect(content().json(docsJson));
    }

}