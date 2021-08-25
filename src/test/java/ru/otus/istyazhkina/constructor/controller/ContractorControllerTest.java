package ru.otus.istyazhkina.constructor.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.istyazhkina.constructor.advice.AppExceptionHandler;
import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.entity.CompanyLegalForm;
import ru.otus.istyazhkina.constructor.domain.entity.ManagerPosition;
import ru.otus.istyazhkina.constructor.security.SecurityConfiguration;
import ru.otus.istyazhkina.constructor.service.ContractorService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContractorController.class)
@Import({ContractorController.class, AppExceptionHandler.class})
@ContextConfiguration(classes = {SecurityConfiguration.class, ControllerTestsConfiguration.class})
class ContractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContractorService contractorService;

    private Company company = Company.builder()
            .id("1")
            .companyName("Рыжий нос")
            .companyLegalForm(CompanyLegalForm.builder().id("1").title("Общество с ограниченной ответственностью").build())
            .inn("34254345453")
            .position(ManagerPosition.builder().id("1").title("Директор").build())
            .managerSurname("Иванов")
            .managerName("Иван")
            .managerSecondName("Иванович")
            .build();
    private CompanyLegalForm companyLegalForm = CompanyLegalForm.builder().id("1").title("Общество с ограниченной ответственностью").build();
    private ManagerPosition managerPosition = ManagerPosition.builder().id("1").title("Директор").build();
    private static final String arrayJsonCompanies = "[{\"id\":\"1\",\"companyLegalForm\":{\"id\":\"1\",\"title\":\"Общество с ограниченной ответственностью\"},\"companyName\":\"Рыжий нос\",\"managerPosition\":{\"id\":\"1\",\"title\":\"Директор\"},\"managerName\":\"Иван\",\"managerSecondName\":\"Иванович\",\"managerSurname\":\"Иванов\",\"inn\":\"34254345453\"}]";
    private static final String jsonSingleCompany = "{\"id\":\"1\",\"companyLegalForm\":{\"id\":\"1\",\"title\":\"Общество с ограниченной ответственностью\"},\"companyName\":\"Рыжий нос\",\"managerPosition\":{\"id\":\"1\",\"title\":\"Директор\"},\"managerName\":\"Иван\",\"managerSecondName\":\"Иванович\",\"managerSurname\":\"Иванов\",\"inn\":\"34254345453\"}";
    private static final String arrayJsonLegalForms = "[{\"id\":\"1\",\"title\":\"Общество с ограниченной ответственностью\"}]";
    private static final String arrayJsonManagerPositions = "[{\"id\":\"1\",\"title\":\"Директор\"}]";


    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnCompaniesList() {
        when(contractorService.getAllCompanies()).thenReturn(List.of(company));
        mockMvc.perform(get("/api/contractors"))
                .andExpect(status().is(200))
                .andExpect(content().json(arrayJsonCompanies));
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnLegalFormsList() {
        when(contractorService.getCompanyLegalForms()).thenReturn(List.of(companyLegalForm));
        mockMvc.perform(get("/api/legal_forms"))
                .andExpect(status().is(200))
                .andExpect(content().json(arrayJsonLegalForms));
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnManagerPositions() {
        when(contractorService.getManagerPositions()).thenReturn(List.of(managerPosition));
        mockMvc.perform(get("/api/manager_positions"))
                .andExpect(status().is(200))
                .andExpect(content().json(arrayJsonManagerPositions));

    }

    @Test
    @WithMockUser
    @SneakyThrows
    void shouldReturnCompanyById() {
        when(contractorService.getCompanyById("1")).thenReturn(company);
        mockMvc.perform(get("/api/contractor/1"))
                .andExpect(status().is(200))
                .andExpect(content().json(jsonSingleCompany));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @SneakyThrows
    void shouldAddCompany() {
        Company newCompany = Company.builder().companyName("Рыжий нос")
                .companyLegalForm(CompanyLegalForm.builder().id("1").title("Общество с ограниченной ответственностью").build())
                .inn("34254345453")
                .position(ManagerPosition.builder().id("1").title("Директор").build())
                .managerSurname("Иванов")
                .managerName("Иван")
                .managerSecondName("Иванович")
                .build();
        when(contractorService.getCompanyLegalFormByCLId("1")).thenReturn(companyLegalForm);
        when(contractorService.getManagerPositionByPositionId("1")).thenReturn(managerPosition);
        when(contractorService.addNewCompany(newCompany)).thenReturn(company);
        mockMvc.perform(post("/contractor/add")
                .contentType("application/json;charset=utf-8")
                .content("{\"id\":null,\"companyLegalForm\":{\"id\":\"1\"},\"companyName\":\"Рыжий нос\",\"managerPosition\":{\"id\":\"1\"},\"managerName\":\"Иван\",\"managerSecondName\":\"Иванович\",\"managerSurname\":\"Иванов\",\"inn\":\"34254345453\"}"))
                .andExpect(status().is(201))
                .andExpect(content().json(jsonSingleCompany));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @SneakyThrows
    void shouldUpdateCompany() {
        Company updateCompany = Company.builder().companyName("Веселый день")
                .companyLegalForm(CompanyLegalForm.builder().id("1").title("Общество с ограниченной ответственностью").build())
                .inn("8594035843958")
                .position(ManagerPosition.builder().id("1").title("Директор").build())
                .managerSurname("Григорьев")
                .managerName("Василий")
                .managerSecondName("Иванович")
                .build();

        Company updatedCompany = Company.builder().companyName("Веселый день")
                .id("2")
                .companyLegalForm(CompanyLegalForm.builder().id("1").title("Общество с ограниченной ответственностью").build())
                .inn("8594035843958")
                .position(ManagerPosition.builder().id("1").title("Директор").build())
                .managerSurname("Григорьев")
                .managerName("Василий")
                .managerSecondName("Иванович")
                .build();

        String jsonContent = "{\"id\":\"2\",\"companyLegalForm\":{\"id\":\"1\"},\"companyName\":\"Веселый день\",\"managerPosition\":{\"id\":\"1\"},\"managerName\":\"Василий\",\"managerSecondName\":\"Иванович\",\"managerSurname\":\"Григорьев\",\"inn\":\"8594035843958\"}";

        when(contractorService.getCompanyLegalFormByCLId("1")).thenReturn(companyLegalForm);
        when(contractorService.getManagerPositionByPositionId("1")).thenReturn(managerPosition);
        when(contractorService.updateCompany("2", updateCompany)).thenReturn(updatedCompany);

        mockMvc.perform(put("/contractor/2")
                .contentType("application/json;charset=utf-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(content().json(jsonContent));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @SneakyThrows
    void shouldDeleteCompany() {
        mockMvc.perform(delete("/contractor/3"))
                .andExpect(status().is(200));
    }
}