package ru.otus.istyazhkina.constructor.service;

import org.assertj.core.util.Lists;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import ru.otus.istyazhkina.constructor.repository.CompanyLegalFormRepository;
import ru.otus.istyazhkina.constructor.repository.CompanyRepository;
import ru.otus.istyazhkina.constructor.repository.DocRepository;
import ru.otus.istyazhkina.constructor.repository.DocumentTypesRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeePositionsRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeeRepository;
import ru.otus.istyazhkina.constructor.repository.ManagerPositionsRepository;
import ru.otus.istyazhkina.constructor.repository.PowerRepository;
import ru.otus.istyazhkina.constructor.service.factory.FormDataConverterFactoryService;
import ru.otus.istyazhkina.constructor.service.impl.ContractorServiceImpl;
import ru.otus.istyazhkina.constructor.service.impl.DocumentConverterImpl;
import ru.otus.istyazhkina.constructor.service.impl.DocumentServiceImpl;
import ru.otus.istyazhkina.constructor.service.impl.EmployeeServiceImpl;
import ru.otus.istyazhkina.constructor.service.impl.TemplateResolverImpl;
import ru.otus.istyazhkina.constructor.service.impl.converters.OrderToRaiseWagesDataConverter;
import ru.otus.istyazhkina.constructor.service.impl.converters.PowerOfAttorneyDataConverter;

import static org.mockito.Mockito.mock;

@SpringBootConfiguration
public class ServiceTestsConfiguration {

    @Bean
    public PowerOfAttorneyDataConverter powerOfAttorneyDataConverter() {
        PowerOfAttorneyDataConverter mock = mock(PowerOfAttorneyDataConverter.class);
        Mockito.when(mock.getTemplateId()).then((Answer<String>) invocationOnMock -> "1");
        return mock;
    }

    @Bean
    public OrderToRaiseWagesDataConverter orderToRaiseWagesDataConverter() {
        OrderToRaiseWagesDataConverter mock = mock(OrderToRaiseWagesDataConverter.class);
        Mockito.when(mock.getTemplateId()).then((Answer<String>) invocationOnMock -> "2");
        return mock;
    }

    @Bean
    public FormDataConverterFactoryService formDataConverterFactoryService() {
        return new FormDataConverterFactoryService(Lists.newArrayList(powerOfAttorneyDataConverter(), orderToRaiseWagesDataConverter()));
    }

    @Bean
    public DocumentConverter documentConverter() {
        return new DocumentConverterImpl();
    }

    @Bean
    public DocumentTypesRepository documentTypesRepository() {
        return mock(DocumentTypesRepository.class);
    }

    @Bean
    public DocRepository docRepository() {
        return mock(DocRepository.class);
    }

    @Bean
    public PowerRepository powerRepository() {
        return mock(PowerRepository.class);
    }

    @Bean
    public DocumentService documentService() {
        return new DocumentServiceImpl(documentTypesRepository(), documentConverter(), powerRepository(), docRepository());
    }

    @Bean
    public TemplateResolver templateResolver() {
        return new TemplateResolverImpl(documentTypesRepository());
    }

    @Bean
    public ContractorService contractorService() {
        return new ContractorServiceImpl(companyRepository(), companyLegalFormRepository(), managerPositionsRepository());
    }

    @Bean
    public EmployeeService employeeService() {
        return new EmployeeServiceImpl(employeeRepository(), employeePositionsRepository());
    }

    @Bean
    public CompanyRepository companyRepository() {
        return mock(CompanyRepository.class);
    }

    @Bean
    public CompanyLegalFormRepository companyLegalFormRepository() {
        return mock(CompanyLegalFormRepository.class);
    }

    @Bean
    public ManagerPositionsRepository managerPositionsRepository() {
        return mock(ManagerPositionsRepository.class);
    }

    @Bean
    public EmployeeRepository employeeRepository() {
        return mock(EmployeeRepository.class);
    }

    @Bean
    public EmployeePositionsRepository employeePositionsRepository() {
        return mock(EmployeePositionsRepository.class);
    }

}