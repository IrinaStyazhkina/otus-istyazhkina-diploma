package ru.otus.istyazhkina.constructor.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.entity.CompanyLegalForm;
import ru.otus.istyazhkina.constructor.domain.entity.ManagerPosition;
import ru.otus.istyazhkina.constructor.repository.CompanyLegalFormRepository;
import ru.otus.istyazhkina.constructor.repository.CompanyRepository;
import ru.otus.istyazhkina.constructor.repository.ManagerPositionsRepository;
import ru.otus.istyazhkina.constructor.service.ContractorService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class ContractorServiceImplTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyLegalFormRepository companyLegalFormRepository;

    @Autowired
    private ManagerPositionsRepository managerPositionsRepository;

    @Autowired
    private ContractorService contractorService;

    private final Company company = Company.builder()
            .id("1")
            .companyName("Рыжий нос")
            .companyLegalForm(CompanyLegalForm.builder().id("1").title("Общество с ограниченной ответственностью").build())
            .inn("34254345453")
            .position(ManagerPosition.builder().id("1").title("Директор").build())
            .managerSurname("Иванов")
            .managerName("Иван")
            .managerSecondName("Иванович")
            .build();

    private final List<Company> companies = List.of(company);

    private final ManagerPosition managerPosition = ManagerPosition.builder().id("1").title("Директор").build();
    private final CompanyLegalForm companyLegalForm = CompanyLegalForm.builder().id("1").title("Общество с ограниченной ответственностью").build();


    @BeforeEach
    void setUp() {
        Mockito.when(companyLegalFormRepository.findById("1")).thenReturn(Optional.of(companyLegalForm));
        Mockito.when(companyLegalFormRepository.findAll()).thenReturn(List.of(companyLegalForm));
        Mockito.when(managerPositionsRepository.findById("1")).thenReturn(Optional.of(managerPosition));
        Mockito.when(managerPositionsRepository.findAll()).thenReturn(List.of(managerPosition));
    }

    @Test
    void shouldReturnAllCompanies() {
        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        List<Company> allCompanies = contractorService.getAllCompanies();
        assertThat(allCompanies).isNotEmpty().containsAll(companies);
    }

    @Test
    @SneakyThrows
    void shouldReturnCompanyById() {
        Mockito.when(companyRepository.findById("1")).thenReturn(Optional.of(company));
        Company companyById = contractorService.getCompanyById("1");
        assertThat(companyById).isEqualTo(company);
    }

    @Test
    void shouldReturnAllManagerPositions() {
        List<ManagerPosition> managerPositions = contractorService.getManagerPositions();
        assertThat(managerPositions).isNotEmpty().containsExactly(managerPosition);
    }

    @Test
    @SneakyThrows
    void shouldReturnManagerPositionById() {
        ManagerPosition managerPositionById = contractorService.getManagerPositionByPositionId("1");
        assertThat(managerPositionById).isEqualTo(managerPosition);
    }

    @Test
    void shouldReturnAllCompanyLegalForms() {
        List<CompanyLegalForm> companyLegalForms = contractorService.getCompanyLegalForms();
        assertThat(companyLegalForms).isNotEmpty().containsExactly(companyLegalForm);
    }

    @Test
    @SneakyThrows
    void shouldReturnCompanyLegalFormById() {
        CompanyLegalForm companyLegalFormById = contractorService.getCompanyLegalFormByCLId("1");
        assertThat(companyLegalFormById).isEqualTo(companyLegalForm);
    }

    @Test
    void shouldAddNewCompany() {
        Company newCompany = Company.builder()
                .id("2")
                .companyName("Тридцать сапожников")
                .companyLegalForm(companyLegalFormRepository.findById("1").get())
                .position(managerPositionsRepository.findById("1").get())
                .managerName("Петр")
                .managerSecondName("Петрович")
                .managerSurname("Веселов")
                .build();
        Mockito.when(companyRepository.save(newCompany)).thenReturn(newCompany);
        Company createdCompany = contractorService.addNewCompany(newCompany);
        assertThat(createdCompany).isEqualTo(newCompany);
    }

    @Test
    @SneakyThrows
    void shouldUpdateCompany() {
        Company companyToUpdate = Company.builder()
                .id("2")
                .companyName("12 негритят")
                .companyLegalForm(companyLegalFormRepository.findById("1").get())
                .position(managerPositionsRepository.findById("1").get())
                .managerName("Лев")
                .managerSecondName("Львович")
                .managerSurname("Львов")
                .build();
        Mockito.when(companyRepository.findById("2")).thenReturn(Optional.of(companyToUpdate));
        Mockito.when(companyRepository.save(companyToUpdate)).thenReturn(companyToUpdate);
        Company updatedCompany = contractorService.updateCompany("2", companyToUpdate);
        assertThat(updatedCompany).isEqualTo(companyToUpdate);

    }

    @Test
    void shouldDeleteCompany() {
        Mockito.doNothing().when(companyRepository).deleteById("3");
        contractorService.deleteCompany("3");
        assertThatNoException();
    }

}