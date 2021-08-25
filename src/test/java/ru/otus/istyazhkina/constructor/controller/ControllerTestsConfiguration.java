package ru.otus.istyazhkina.constructor.controller;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.otus.istyazhkina.constructor.service.ContractorService;
import ru.otus.istyazhkina.constructor.service.DocumentService;
import ru.otus.istyazhkina.constructor.service.EmployeeService;
import ru.otus.istyazhkina.constructor.service.factory.FormDataConverterFactoryService;

import static org.mockito.Mockito.mock;

@SpringBootConfiguration
public class ControllerTestsConfiguration {

    @Bean
    public ContractorService contractorService() {
        return mock(ContractorService.class);
    }

    @Bean
    public EmployeeService employeeService() {
        return mock(EmployeeService.class);
    }

    @Bean
    public DocumentService documentService() {
        return mock(DocumentService.class);
    }

    @Bean
    public FormDataConverterFactoryService formDataConverterFactoryService() {
        return mock(FormDataConverterFactoryService.class);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
