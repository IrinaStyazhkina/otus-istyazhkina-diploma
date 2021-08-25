package ru.otus.istyazhkina.constructor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("manager_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerPosition {
    @Id
    private String id;
    @Field("title")
    private String title;
}
