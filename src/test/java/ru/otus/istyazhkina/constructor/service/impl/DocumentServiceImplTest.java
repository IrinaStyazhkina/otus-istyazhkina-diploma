package ru.otus.istyazhkina.constructor.service.impl;

import lombok.SneakyThrows;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.istyazhkina.constructor.domain.entity.Doc;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.domain.entity.Power;
import ru.otus.istyazhkina.constructor.repository.DocRepository;
import ru.otus.istyazhkina.constructor.repository.DocumentTypesRepository;
import ru.otus.istyazhkina.constructor.repository.PowerRepository;
import ru.otus.istyazhkina.constructor.service.DocumentService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DocumentServiceImplTest {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentTypesRepository documentTypesRepository;

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private PowerRepository powerRepository;

    private final DocumentType documentType = DocumentType.builder()
            .id("1")
            .alias("Доверенность")
            .link("docs/powerOfAttorney")
            .build();

    private final Doc doc = Doc.builder()
            .id("1")
            .file(new Binary(new byte[]{0, 1}))
            .fileName("Test file")
            .build();

    private final Power power = Power.builder()
            .id("1")
            .name("Представительство")
            .value("Представлять интересы")
            .build();

    @Test
    void shouldReturnDocumentTypesList() {
        Mockito.when(documentTypesRepository.findAll()).thenReturn(List.of(documentType));
        List<DocumentType> allDocumentTypes = documentService.getAllDocumentTypes();
        assertThat(allDocumentTypes).isNotEmpty().containsExactly(documentType);
    }

    @Test
    void shouldReturnAllDocuments() {
        Mockito.when(docRepository.findAll()).thenReturn(List.of(doc));
        List<Doc> allDocuments = documentService.getAllDocuments();
        assertThat(allDocuments).isNotEmpty().containsExactly(doc);
    }

    @Test
    @SneakyThrows
    void shouldReturnDocumentById() {
        Mockito.when(docRepository.findById("1")).thenReturn(Optional.of(doc));
        Doc documentById = documentService.getDocumentById("1");
        assertThat(documentById).isEqualTo(doc);
    }

    @Test
    void shouldReturnAllPowers() {
        Mockito.when(powerRepository.findAll()).thenReturn(List.of(power));
        List<Power> allPowers = documentService.getAllPowers();
        assertThat(allPowers).isNotEmpty().containsExactly(power);
    }

}