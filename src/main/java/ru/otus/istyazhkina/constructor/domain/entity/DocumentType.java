package ru.otus.istyazhkina.constructor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "document_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentType {

    @Id
    private String id;
    @Field("alias")
    private String alias;
    @Field("link")
    private String link;
}
