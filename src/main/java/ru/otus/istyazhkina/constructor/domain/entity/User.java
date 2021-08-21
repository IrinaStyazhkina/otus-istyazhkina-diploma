package ru.otus.istyazhkina.constructor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.istyazhkina.constructor.security.authorities.Authority;

import java.util.Set;

@Document(collection = "user")
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    private String id;

    @Field(name = "login")
    private String login;

    @Field(name = "password")
    private String password;

    @Field(name = "authorities")
    private Set<Authority> authorities;

}
