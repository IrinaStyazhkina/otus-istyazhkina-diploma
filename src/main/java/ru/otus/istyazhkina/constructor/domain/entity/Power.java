package ru.otus.istyazhkina.constructor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("power")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Power {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("value")
    private String value;
}
