package ru.otus.istyazhkina.constructor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.istyazhkina.constructor.domain.entity.Company;

public interface ContractorRepository extends MongoRepository<Company, String> {
}
