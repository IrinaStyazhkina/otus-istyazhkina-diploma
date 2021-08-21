package ru.otus.istyazhkina.constructor.domain.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentTypeDTO {

    String id;
    String alias;
    String link;

    public static DocumentType toEntity(DocumentTypeDTO dto) {
        return DocumentType.builder()
                .id(dto.getId())
                .alias(dto.getAlias())
                .link(dto.getLink())
                .build();
    }

    public static DocumentTypeDTO toDto(DocumentType documentType) {
        return DocumentTypeDTO.builder()
                .id(documentType.getId())
                .alias(documentType.getAlias())
                .link(documentType.getLink())
                .build();
    }

}
