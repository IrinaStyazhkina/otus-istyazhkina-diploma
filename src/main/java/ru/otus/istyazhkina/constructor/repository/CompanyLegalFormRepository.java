package ru.otus.istyazhkina.constructor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.istyazhkina.constructor.domain.entity.CompanyLegalForm;

public interface CompanyLegalFormRepository extends MongoRepository<CompanyLegalForm, String> {
}
