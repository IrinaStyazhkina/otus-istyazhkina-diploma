package ru.otus.istyazhkina.constructor.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.istyazhkina.constructor.domain.entity.Company;
import ru.otus.istyazhkina.constructor.domain.entity.CompanyLegalForm;
import ru.otus.istyazhkina.constructor.domain.entity.DocumentType;
import ru.otus.istyazhkina.constructor.domain.entity.Employee;
import ru.otus.istyazhkina.constructor.domain.entity.EmployeePosition;
import ru.otus.istyazhkina.constructor.domain.entity.ManagerPosition;
import ru.otus.istyazhkina.constructor.domain.entity.Power;
import ru.otus.istyazhkina.constructor.domain.entity.User;
import ru.otus.istyazhkina.constructor.repository.CompanyLegalFormRepository;
import ru.otus.istyazhkina.constructor.repository.ContractorRepository;
import ru.otus.istyazhkina.constructor.repository.DocumentTypesRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeePositionsRepository;
import ru.otus.istyazhkina.constructor.repository.EmployeeRepository;
import ru.otus.istyazhkina.constructor.repository.ManagerPositionsRepository;
import ru.otus.istyazhkina.constructor.repository.PowerRepository;
import ru.otus.istyazhkina.constructor.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.otus.istyazhkina.constructor.security.authorities.Authority.ROLE_ADMIN;
import static ru.otus.istyazhkina.constructor.security.authorities.Authority.ROLE_USER;


@ChangeLog(order = "001")
public class InitDatabaseChangeLog {

    private final List<EmployeePosition> employeePositions = new ArrayList<>();
    private final List<CompanyLegalForm> companyLegalForms = new ArrayList<>();
    private final List<ManagerPosition> managerPositions = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "irinastyazhkina", runAlways = true)
    public void dropDB(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "001", id = "addPositions", author = "irinastyazhkina", runAlways = true)
    public void addPositions(EmployeePositionsRepository employeePositionsRepository) {
        EmployeePosition lawyer = employeePositionsRepository.save(EmployeePosition.builder().title("Юрист").build());
        EmployeePosition accountant = employeePositionsRepository.save(EmployeePosition.builder().title("Бухгалтер").build());
        EmployeePosition hr = employeePositionsRepository.save(EmployeePosition.builder().title("Специалист по кадрам").build());
        EmployeePosition loader = employeePositionsRepository.save(EmployeePosition.builder().title("Грузчик").build());
        EmployeePosition foreman = employeePositionsRepository.save(EmployeePosition.builder().title("Прораб").build());
        EmployeePosition sailor = employeePositionsRepository.save(EmployeePosition.builder().title("Специалист по продажам").build());
        employeePositions.addAll(List.of(lawyer, accountant, hr, loader, foreman, sailor));
    }

    @ChangeSet(order = "002", id = "addEmployee", author = "irinastyazhkina", runAlways = true)
    public void addEmployee(EmployeeRepository employeeRepository) {
        employeeRepository.save(Employee.builder()
                .name("Петр")
                .secondName("Владимирович")
                .surname("Желтухин")
                .passIssued("УВД Центрального района г.Омск")
                .passNumber("3625 674534")
                .passDate("22.07.1996")
                .position(employeePositions.get(2))
                .build());

        employeeRepository.save(Employee.builder()
                .name("Кирилл")
                .secondName("Геннадьевич")
                .surname("Рябов")
                .passIssued("УВД г. Пятигорска")
                .passNumber("2657 453627")
                .passDate("22.07.2000")
                .position(employeePositions.get(4))
                .build());

        employeeRepository.save(Employee.builder()
                .name("Денис")
                .secondName("Андреевич")
                .surname("Мокров")
                .passIssued("ОВД Приморского района г. Санкт-Петербург")
                .passNumber("2345 345261")
                .passDate("13.12.2000")
                .position(employeePositions.get(1))
                .build());

        employeeRepository.save(Employee.builder()
                .name("Асель")
                .secondName("Геннадьевна")
                .surname("Степная")
                .passIssued("ОВД Приморского района г. Санкт-Петербург")
                .passNumber("2345 456261")
                .passDate("13.12.2012")
                .position(employeePositions.get(2))
                .build());
    }

    @ChangeSet(order = "003", id = "addCompanyLegalForms", author = "irinastyazhkina", runAlways = true)
    public void addCompanyLegalForms(CompanyLegalFormRepository companyLegalFormRepository) {
        CompanyLegalForm llc = companyLegalFormRepository.save(CompanyLegalForm.builder()
                .title("Общество с ограниченной ответственностью")
                .build());

        CompanyLegalForm ao = companyLegalFormRepository.save(CompanyLegalForm.builder()
                .title("Акционерное общество")
                .build());

        CompanyLegalForm publicAO = companyLegalFormRepository.save(CompanyLegalForm.builder()
                .title("Публичное акционерное общество")
                .build());
        companyLegalForms.addAll(List.of(llc, ao, publicAO));
    }

    @ChangeSet(order = "004", id = "addManagerPositions", author = "irinastyazhkina", runAlways = true)
    public void addManagerPositions(ManagerPositionsRepository managerPositionsRepository) {
        ManagerPosition generalDirector = managerPositionsRepository.save(ManagerPosition.builder()
                .title("Генеральный директор")
                .build());

        ManagerPosition director = managerPositionsRepository.save(ManagerPosition.builder()
                .title("Директор")
                .build());

        ManagerPosition bankruptcyCommissioner = managerPositionsRepository.save(ManagerPosition.builder()
                .title("Арбитражный управляющий")
                .build());
        managerPositions.addAll(List.of(generalDirector, director, bankruptcyCommissioner));
    }

    @ChangeSet(order = "005", id = "addContractors", author = "irinastyazhkina", runAlways = true)
    public void addContractors(ContractorRepository contractorRepository) {
        contractorRepository.save(Company.builder()
                .companyLegalForm(companyLegalForms.get(0))
                .companyName("Рога и копыта")
                .inn("4763567722")
                .position(managerPositions.get(0))
                .managerName("Остап")
                .managerSurname("Бендер")
                .build());

        contractorRepository.save(Company.builder()
                .companyLegalForm(companyLegalForms.get(1))
                .companyName("Сытые котики")
                .inn("2600896725")
                .position(managerPositions.get(1))
                .managerName("Элеонора")
                .managerSecondName("Викторовна")
                .managerSurname("Сметанник")
                .build());

        contractorRepository.save(Company.builder().
                companyLegalForm(companyLegalForms.get(2))
                .companyName("Зеленоглазое такси")
                .inn("34256542566")
                .position(managerPositions.get(2))
                .managerName("Иван")
                .managerSecondName("Игоревич")
                .managerSurname("Белоусов")
                .build());
    }

    @ChangeSet(order = "006", id = "addDocumentTypes", author = "irinastyazhkina", runAlways = true)
    public void addDocumentTypes(DocumentTypesRepository documentTypesRepository) {
        documentTypesRepository.save(DocumentType.builder()
                .id("1")
                .alias("Доверенность")
                .link("docs/powerOfAttorney")
                .build());

        documentTypesRepository.save(DocumentType.builder()
                .id("2")
                .alias("Приказ о повышении заработной платы")
                .link("docs/orderToRaiseWages")
                .build());
    }

    @ChangeSet(order = "007", id = "addPowers", author = "irinastyazhkina", runAlways = true)
    public void addPowers(PowerRepository powerRepository) {
        powerRepository.save(Power.builder()
                .name("Предствительство в судах")
                .value("Осуществлять от имени Общества представительство в судах Российской Федерации, " +
                        "в том числе в арбитражных, федеральных судах общей юрисдикции и у мировых судей, " +
                        "совершать все процессуальные действия, предусмотренные действующим законодательством, " +
                        "в том числе подписывать исковые заявления и отзывы на них, предъявлять их в суд, " +
                        "подписывать заявления об обеспечении иска, иные заявления, ходатайства и жалобы, " +
                        "предъявлять встречные иски и подписывать заявления о пересмотре судебных актов по новым " +
                        "или вновь открывшимся обстоятельствам, обжаловать судебные акты арбитражных судов, " +
                        "судов общей юрисдикции и мировых судей, в том числе судебные постановления " +
                        "(с правом подписи апелляционных, кассационных и надзорных жалоб, заявлений), " +
                        "обжаловать постановления и действия (бездействия) судебного пристава-исполнителя;")
                .build());

        powerRepository.save(Power.builder()
                .name("Получение корреспонденции")
                .value("Получать любые почтовые отправления, адресованные Обществу, в отделениях почтовой связи," +
                        "расписываться и осуществлять все иные необходимые действия, связанные с выполнением данного поручения")
                .build());
    }

    @ChangeSet(order = "008", id = "initUsers", author = "irinastyazhkina", runAlways = true)
    public void initUsers(UserRepository userRepository) {
        userRepository.save(new User("1", "simple_user", "$2a$10$WSj712oJc7/f1SSpL1bA1.LkuQG9/qAQ5o9f9SkWNIW9fe/eKXv02", Set.of(ROLE_USER)));
        userRepository.save(new User("2", "admin_user", "$2a$10$lTIh4g77FK3X5Jue6x0/huW.DptxzOBreN8xzMeNWfGZNQWKW/qMe", Set.of(ROLE_ADMIN)));
    }

}
