package ru.otus.istyazhkina.constructor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;

public interface DocumentTypesRepository extends MongoRepository<DocumentType, String> {
}
