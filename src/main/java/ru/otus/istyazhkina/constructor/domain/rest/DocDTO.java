package ru.otus.istyazhkina.constructor.domain.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import ru.otus.istyazhkina.constructor.domain.entity.Doc;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocDTO {

    private String id;
    private String fileName;
    private Binary file;

    public static Doc toEntity(DocDTO dto) {
        return Doc.builder()
                .id(dto.getId())
                .fileName(dto.getFileName())
                .file(dto.getFile())
                .build();
    }

    public static DocDTO toDto(Doc doc) {
        return DocDTO.builder()
                .id(doc.getId())
                .fileName(doc.getFileName())
                .file(doc.getFile())
                .build();
    }
}
