package ru.otus.istyazhkina.constructor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.istyazhkina.constructor.domain.entity.Company;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
