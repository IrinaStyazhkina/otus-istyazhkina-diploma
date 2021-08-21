package ru.otus.istyazhkina.constructor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "doc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doc {

    @Id
    private String id;
    @Field("file_name")
    private String fileName;
    @Field
    private Binary file;

}
