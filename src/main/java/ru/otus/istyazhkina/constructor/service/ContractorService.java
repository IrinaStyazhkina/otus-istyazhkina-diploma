package ru.otus.istyazhkina.constructor.service;

import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.entity.CompanyLegalForm;
import ru.otus.istyazhkina.constructor.domain.entity.ManagerPosition;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;

import java.util.List;

public interface ContractorService {

    List<Company> getAllCompanies();

    void deleteCompany(String contractorId);

    List<CompanyLegalForm> getCompanyLegalForms();

    List<ManagerPosition> getManagerPositions();

    CompanyLegalForm getCompanyLegalFormByCLId(String id) throws DataNotFoundException;

    ManagerPosition getManagerPositionByPositionId(String id) throws DataNotFoundException;

    Company addNewCompany(Company company);

    Company updateCompany(String contractorId, Company company) throws DataNotFoundException;

    Company getCompanyById(String contractorId) throws DataNotFoundException;
}
