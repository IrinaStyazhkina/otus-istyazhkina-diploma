package ru.otus.istyazhkina.constructor.service.impl.converters;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.morpher.ws3.russian.DeclensionResult;
import ru.morpher.ws3.russian.RussianClient;
import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.rest.FormData;
import ru.otus.istyazhkina.constructor.repository.CompanyRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeeRepository;
import ru.otus.istyazhkina.constructor.service.FormDataConverter;
import ru.otus.istyazhkina.constructor.service.impl.MorpherService;

import java.util.HashMap;

@Service
@Qualifier("OrderToRaiseWagesDataConverter")
@RequiredArgsConstructor
public class OrderToRaiseWagesDataConverter implements FormDataConverter {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final MorpherService morpherService;


    @Override
    @SneakyThrows
    public HashMap<String, Object> convertFormDataToMap(FormData formData) {
        HashMap<String, Object> data = new HashMap<>();
        Employee employee = employeeRepository.findById(formData.getEmployee().getId()).get();
        Company company = companyRepository.findById(formData.getCompany().getId()).get();
        RussianClient russian = morpherService.russianMorpher();
        DeclensionResult employeePosition = russian.declension(employee.getPosition().getTitle());
        DeclensionResult employeeNameDat = russian.declension(employee.getName());
        DeclensionResult employeeSecondNameDat = russian.declension(employee.getSecondName());
        DeclensionResult employeeSurnameDat = russian.declension(employee.getSurname());

        data.put("legalForm", company.getCompanyLegalForm().getTitle());
        data.put("companyName", company.getCompanyName());
        data.put("city", formData.getCity());
        data.put("dateOfDocument", formData.getDocDate());
        data.put("newSalaryDate", formData.getSalaryDate());
        data.put("position", employeePosition.dative);
        data.put("name_dat", employeeNameDat.dative + " " + employeeSecondNameDat.dative + " " + employeeSurnameDat.dative);
//        data.put("position", employee.getPosition().getTitle());
        data.put("name", employee.getName());
        data.put("secondName", employee.getSecondName());
        data.put("surname", employee.getSurname());
        data.put("rubles", formData.getSalaryRub());
        data.put("kopec", formData.getSalaryCop());
        data.put("managerPosition", company.getPosition().getTitle());

        StringBuilder managerName = new StringBuilder();
        managerName.append(company.getManagerSurname())
                .append(" ")
                .append(company.getManagerName().charAt(0))
                .append(".");
        if (company.getManagerSecondName() != null) {
            managerName.append(company.getManagerSecondName().charAt(0))
                    .append(".");
        }
        data.put("manager", managerName);

        return data;
    }
}
