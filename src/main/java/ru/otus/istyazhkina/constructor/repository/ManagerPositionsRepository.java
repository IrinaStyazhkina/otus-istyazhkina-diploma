package ru.otus.istyazhkina.constructor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.istyazhkina.constructor.domain.entity.ManagerPosition;

public interface ManagerPositionsRepository extends MongoRepository<ManagerPosition, String> {
}
