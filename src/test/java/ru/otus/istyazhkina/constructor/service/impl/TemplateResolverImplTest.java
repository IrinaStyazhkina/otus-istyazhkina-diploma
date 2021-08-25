package ru.otus.istyazhkina.constructor.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.repository.DocumentTypesRepository;
import ru.otus.istyazhkina.constructor.service.TemplateResolver;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class TemplateResolverImplTest {

    @Autowired
    private DocumentTypesRepository documentTypesRepository;

    @Autowired
    private TemplateResolver templateResolver;

    private DocumentType documentType = DocumentType.builder()
            .id("1")
            .alias("Доверенность")
            .link("docs/powerOfAttorney")
            .build();

    @Test
    @SneakyThrows
    void shouldReturnDocumentTypeByTemplateId() {
        Mockito.when(documentTypesRepository.findById("1")).thenReturn(Optional.of(documentType));
        DocumentType documentType = templateResolver.resolveTemplate("1");
        assertThat(documentType).isEqualTo(documentType);
    }


}