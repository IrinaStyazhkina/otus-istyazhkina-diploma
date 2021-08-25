package ru.otus.istyazhkina.constructor.service.impl.converters;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.morpher.ws3.russian.DeclensionResult;
import ru.morpher.ws3.russian.RussianClient;
import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.entity.Power;
import ru.otus.istyazhkina.constructor.domain.rest.FormData;
import ru.otus.istyazhkina.constructor.exception.DataNotFoundException;
import ru.otus.istyazhkina.constructor.repository.CompanyRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeeRepository;
import ru.otus.istyazhkina.constructor.repository.PowerRepository;
import ru.otus.istyazhkina.constructor.service.FormDataConverter;
import ru.otus.istyazhkina.constructor.service.TemplateResolver;
import ru.otus.istyazhkina.constructor.service.impl.MorpherService;

import java.util.HashMap;

@Service("PowerOfAttorneyDataConverter")
@RequiredArgsConstructor
public class PowerOfAttorneyDataConverter implements FormDataConverter {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final PowerRepository powerRepository;
    private final MorpherService morpherService;
    private final TemplateResolver templateResolver;

    @Override
    @SneakyThrows
    public HashMap<String, Object> convertFormDataToMap(FormData formData) {
        HashMap<String, Object> data = new HashMap<>();
        Employee employee = employeeRepository.findById(formData.getEmployee().getId()).get();
        Company company = companyRepository.findById(formData.getCompany().getId()).get();
        RussianClient russian = morpherService.russianMorpher();
        DeclensionResult employeePosition = russian.declension(employee.getPosition().getTitle());
        DeclensionResult employeeName = russian.declension(employee.getName());
        DeclensionResult employeeSecondName = russian.declension(employee.getSecondName());
        DeclensionResult employeeSurname = russian.declension(employee.getSurname());
        DeclensionResult managerPosition = russian.declension(company.getPosition().getTitle());
        DeclensionResult managerName = russian.declension(company.getManagerName());
        DeclensionResult managerSurname = russian.declension(company.getManagerSurname());
        String managerNameAcc;
        if (company.getManagerSecondName() != null) {
            DeclensionResult managerSecondName = russian.declension(company.getManagerSecondName());
            managerNameAcc = managerName.genitive + " " + managerSecondName.genitive + " " + managerSurname.genitive;
        } else {
            managerNameAcc = managerName.genitive + " " + managerSurname.genitive;
        }

        data.put("managerNameAcc", managerNameAcc);

        data.put("legalForm", company.getCompanyLegalForm().getTitle());
        data.put("companyName", company.getCompanyName());
        data.put("city", formData.getCity());
        data.put("dateOfDocument", formData.getDocDate());

        data.put("managerPositionAcc", managerPosition.accusative);

        data.put("employeeName", employee.getName() + " " + employee.getSecondName() + " " + employee.getSurname());
        data.put("employeeNameAcc", employeeName.accusative + " " + employeeSecondName.accusative + " " + employeeSurname.accusative);
        data.put("managerPosition", company.getPosition().getTitle());
        data.put("employeePassNumber", employee.getPassNumber());
        data.put("employeePassDate", employee.getPassDate());
        data.put("employeePassIssued", employee.getPassIssued());
        Power power = powerRepository.findById(formData.getPower().getId()).orElseThrow(() -> new DataNotFoundException("No power found by provided id!"));
        data.put("powers", power.getValue());
        data.put("expireDate", formData.getExpireDate());

        StringBuilder managerNameShort = new StringBuilder();
        managerNameShort.append(company.getManagerSurname())
                .append(" ")
                .append(company.getManagerName().charAt(0))
                .append(".");
        if (company.getManagerSecondName() != null) {
            managerNameShort.append(company.getManagerSecondName().charAt(0))
                    .append(".");
        }
        data.put("managerName", managerNameShort);

        return data;
    }

    @Override
    public String getTemplateId() {
        return "1";
    }

}
