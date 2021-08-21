package ru.otus.istyazhkina.constructor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.entity.CompanyLegalForm;
import ru.otus.istyazhkina.constructor.domain.entity.ManagerPosition;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.repository.CompanyLegalFormRepository;
import ru.otus.istyazhkina.constructor.repository.CompanyRepository;
import ru.otus.istyazhkina.constructor.repository.ManagerPositionsRepository;
import ru.otus.istyazhkina.constructor.service.ContractorService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContractorServiceImpl implements ContractorService {

    private final CompanyRepository companyRepository;
    private final CompanyLegalFormRepository companyLegalFormRepository;
    private final ManagerPositionsRepository managerPositionsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(String contractorId) throws DataNotFoundException {
        return companyRepository.findById(contractorId).orElseThrow(() -> new DataNotFoundException("No company found by provided id!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyLegalForm> getCompanyLegalForms() {
        return companyLegalFormRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManagerPosition> getManagerPositions() {
        return managerPositionsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyLegalForm getCompanyLegalFormByCLId(String id) throws DataNotFoundException {
        return companyLegalFormRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No legal form found by provided id!"));
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerPosition getManagerPositionByPositionId(String id) throws DataNotFoundException {
        return managerPositionsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No manager position found by provided id!"));
    }

    @Override
    @Transactional
    public Company addNewCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public Company updateCompany(String contractorId, Company company) throws DataNotFoundException {
        Company companyFromDB = companyRepository.findById(contractorId).orElseThrow(() -> new DataNotFoundException("Company by provided id is not found!"));
        companyFromDB.setCompanyLegalForm(company.getCompanyLegalForm());
        companyFromDB.setCompanyName(company.getCompanyName());
        companyFromDB.setPosition(company.getPosition());
        companyFromDB.setManagerName(company.getManagerName());
        companyFromDB.setManagerSecondName(company.getManagerSecondName());
        companyFromDB.setManagerSurname(company.getManagerSurname());
        return companyRepository.save(companyFromDB);
    }

    @Override
    @Transactional
    public void deleteCompany(String contractorId) {
        companyRepository.deleteById(contractorId);
    }


}
