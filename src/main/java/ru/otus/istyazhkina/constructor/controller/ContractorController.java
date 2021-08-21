package ru.otus.istyazhkina.constructor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.rest.CompanyDTO;
import ru.otus.istyazhkina.constructor.domain.rest.CompanyLegalFormDTO;
import ru.otus.istyazhkina.constructor.domain.rest.ManagerPositionDTO;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.service.ContractorService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    @GetMapping("api/contractors")
    public List<CompanyDTO> getAllCompanies() {
        return contractorService.getAllCompanies()
                .stream()
                .map(CompanyDTO::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/legal_forms")
    public List<CompanyLegalFormDTO> getCompanyLegalForms() {
        return contractorService.getCompanyLegalForms()
                .stream()
                .map(CompanyLegalFormDTO::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/contractor/{contractorId}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO getCompanyById(@PathVariable("contractorId") String contractorId) throws DataNotFoundException {
        return CompanyDTO.toDto(contractorService.getCompanyById(contractorId));
    }

    @GetMapping("/api/manager_positions")
    public List<ManagerPositionDTO> getManagerPositions() {
        return contractorService.getManagerPositions()
                .stream()
                .map(ManagerPositionDTO::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/contractor/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO addCompany(@Valid @RequestBody CompanyDTO companyDTO) throws DataNotFoundException {
        Company company = CompanyDTO.toEntity(companyDTO);
        company.setCompanyLegalForm(contractorService.getCompanyLegalFormByCLId(companyDTO.getCompanyLegalForm().getId()));
        company.setPosition(contractorService.getManagerPositionByPositionId(companyDTO.getManagerPosition().getId()));
        return CompanyDTO.toDto(contractorService.addNewCompany(company));
    }

    @PutMapping("/contractor/{contractorId}")
    public CompanyDTO updateCompany(@PathVariable("contractorId") String contractorId, @Valid @RequestBody CompanyDTO companyDTO) throws DataNotFoundException {
        Company company = CompanyDTO.toEntity(companyDTO);
        company.setCompanyLegalForm(contractorService.getCompanyLegalFormByCLId(companyDTO.getCompanyLegalForm().getId()));
        company.setPosition(contractorService.getManagerPositionByPositionId(companyDTO.getManagerPosition().getId()));
        return CompanyDTO.toDto(contractorService.updateCompany(contractorId, company));
    }

    @DeleteMapping("/contractor/{contractorId}")
    public void deleteCompany(@PathVariable("contractorId") String contractorId) {
        contractorService.deleteCompany(contractorId);
    }
}
