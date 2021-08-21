package ru.otus.istyazhkina.constructor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.istyazhkina.constructor.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login);
}