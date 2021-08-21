package ru.otus.istyazhkina.constructor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.service.TemplateResolver;

@Controller
@RequiredArgsConstructor
public class PagesController {

    private final TemplateResolver templateResolver;

    @GetMapping("/template")
    public String getTemplatesPage() {
        return "template";
    }

    @GetMapping("/form/{templateId}")
    public String getFormPage(@PathVariable("templateId") String templateId) {
        return "form";
    }

    @GetMapping("/template/{templateId}")
    public String getTemplateByTemplateId(@PathVariable("templateId") String templateId) throws DataNotFoundException {
        DocumentType documentType = templateResolver.resolveTemplate(templateId);
        return documentType.getLink();
    }

    @GetMapping("/contractor/add")
    public String getContractorsPage() {
        return "contractor";
    }

    @GetMapping("/contractor/all")
    public String getContractorsListPage() {
        return "contractors";
    }

    @GetMapping("/contractor/{contractorId}")
    public String getExistingContractorPage(@PathVariable("contractorId") String contractorId) {
        return "contractor";
    }

    @GetMapping("/employee/add")
    public String getEmployeesPage() {
        return "employee";
    }

    @GetMapping("employee/all")
    public String getEmployeesListPage() {
        return "employees";
    }

    @GetMapping("/employee/{employeeId}")
    public String getExistingEmployeePage(@PathVariable("employeeId") String employeeId) {
        return "employee";
    }

    @GetMapping("/documents")
    public String getExistingDocuments() {
        return "documents";
    }


}
