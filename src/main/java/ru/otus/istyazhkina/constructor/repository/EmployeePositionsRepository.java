package ru.otus.istyazhkina.constructor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.istyazhkina.constructor.domain.entity.EmployeePosition;

public interface EmployeePositionsRepository extends MongoRepository<EmployeePosition, String> {
}
