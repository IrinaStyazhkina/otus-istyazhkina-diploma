function loadAllEmployees() {
    $.get("/api/employees").done(function (employees) {
        employees.forEach(function (employee) {
            $("tbody").append(`
                <tr class="table_row">
                    <td class="table_cell">${employee.id}</td>
                    <td class="table_cell">${employee.name}</td>
                    <td class="table_cell">${employee.secondName}</td>
                    <td class="table_cell">${employee.surname}</td>
                    <td class="table_cell">${employee.employeePositionDTO.title}</td>
                    <td class="table_cell">
                        <a class="table_edit-link" href="/employee/${employee.id}">Просмотр</a>
                    </td>
                    <td class="table_cell">
                        <button onclick="deleteEmployee('${employee.id}')" class="delete_button" type="button">Удалить</button>
                    </td>
                </tr>
            `);
        })
    });
}

function loadEmployee() {
    const path = window.location.pathname;
    const pathLastPart = path.substr(path.lastIndexOf("/") + 1);
    if (pathLastPart === "add") {
        $(".form_wrapper").append(`
            <form action="/employee/add" class="item_form" id="add-employee" method="post" onsubmit="addEmployee(); return false">
                    <label class="item_label" for="employee_name">Имя</label>
                    <input class="item_input" id="employee_name" value="" required>
                    <label class="item_label" for="employee_second_name">Отчество</label>
                    <input class="item_input" id="employee_second_name" value="">
                    <label class="item_label" for="employee_surname">Фамилия</label>
                    <input class="item_input" id="employee_surname" value="" required>
                    <label class="item_label" for="employee_pass_number">Серия и номер паспорта</label>
                    <input class="item_input" id="employee_pass_number" value="" required>
                    <label class="item_label" for="employee_pass_date">Дата выдачи паспорта</label>
                    <input class="item_input" id="employee_pass_date" value="" required>
                    <label class="item_label" for="employee_pass_issued">Орган, выдавший паспорт</label>
                    <input class="item_input" id="employee_pass_issued" value="" required>  
                    <label class="item_label" for="employee_position">Должность</label>
                    <select class="item_select" id="employee_position">
                    </select>
                    <button class="item_btn" type="submit" id="save">Сохранить</button>
                </form>
        `);
        getPositions();
    } else {
        $.get("/api/employee/" + pathLastPart).done(function (employee) {
            $(".form_wrapper").append(`
                <form class="item_form" id="edit-employee" action="/employee/${pathLastPart}" method="put" name="employee" onsubmit="updateEmployee(); return false;">
                    <label class="item_label" for="employee_id">ID</label>
                    <input class="item_input book_input--disabled" id="employee_id"  value="${employee.id}" disabled>
                    <label class="item_label" for="employee_name">Имя</label>
                    <input class="item_input" id="employee_name" value="${employee.name}" required>
                    <label class="item_label" for="employee_second_name">Отчество</label>
                    <input class="item_input" id="employee_second_name" value="${employee.secondName}">
                    <label class="item_label" for="employee_surname">Фамилия</label>
                    <input class="item_input" id="employee_surname" value="${employee.surname}" required>
                    <label class="item_label" for="employee_pass_number">Серия и номер паспорта</label>
                    <input class="item_input" id="employee_pass_number" value="${employee.passNumber}" required>
                    <label class="item_label" for="employee_pass_date">Дата выдачи паспорта</label>
                    <input class="item_input" id="employee_pass_date" value="${employee.passDate}" required>
                    <label class="item_label" for="employee_pass_issued">Орган, выдавший паспорт</label>
                    <input class="item_input" id="employee_pass_issued" value="${employee.passIssued}" required>  
                    <label class="item_label" for="employee_position">Должность</label>
                    <select class="item_select" id="employee_position">
                    </select>
                    <button class="item_btn" type="submit" id="save">Сохранить</button>
                </form> 
            `);
            getPositionsAndSelect(employee.employeePositionDTO);
        });
    }
}

function addEmployee() {
    const form = $("#add-employee");
    const url = form.attr("action");
    const name = document.querySelector("#employee_name").value;
    const secondName = document.querySelector("#employee_second_name").value;
    const surname = document.querySelector("#employee_surname").value;
    const position_id = document.querySelector("#employee_position").value;
    const passNumber = document.querySelector("#employee_pass_number").value;
    const passDate = document.querySelector("#employee_pass_date").value;
    const passIssued = document.querySelector("#employee_pass_issued").value;
    const employee = {
        'id': null,
        'name': name,
        'secondName': secondName,
        'surname': surname,
        'employeePositionDTO': {'id': position_id},
        'passNumber': passNumber,
        'passDate': passDate,
        'passIssued': passIssued
    };

    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(employee),
        contentType: "application/json;charset=utf-8",
        success: function () {
            alert("Employee is successfully added!");
            goToEmployeesPage();
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

function updateEmployee() {
    const form = $("#edit-employee");
    const url = form.attr("action");
    const id = document.querySelector("#employee_id").value;
    const name = document.querySelector("#employee_name").value;
    const secondName = document.querySelector("#employee_second_name").value;
    const surname = document.querySelector("#employee_surname").value;
    const position_id = document.querySelector("#employee_position").value;
    const passNumber = document.querySelector("#employee_pass_number").value;
    const passDate = document.querySelector("#employee_pass_date").value;
    const passIssued = document.querySelector("#employee_pass_issued").value;

    const employee = {
        'id': id,
        'name': name,
        'secondName': secondName,
        'surname': surname,
        'employeePositionDTO': {'id': position_id},
        'passNumber': passNumber,
        'passDate': passDate,
        'passIssued': passIssued
    };

    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(employee),
        success: function () {
            alert("Employee is successfully updated!");
            goToEmployeesPage();
        },
        error: function (error) {
            alert(error.responseText);
        }
    });

}

function deleteEmployee(employeeId) {
    $.ajax({
        url: "/employee/" + employeeId,
        method: "DELETE",
        success: function () {
            alert("Employee with id " + employeeId + " is successfully deleted!");
            goToEmployeesPage();
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

function goToEmployeesPage() {
    location.href = "/employee/all";
}

function getPositions() {
    $.get("/api/positions").done(function (positions) {
        positions.forEach(function (position) {
            $("#employee_position").append(`
                    <option value=${position.id}>
                        ${position.title}
                    </option>
                `);
        })
    });
}

function getPositionsAndSelect(position) {
    $.get("/api/positions").done(function (positions) {
        positions.forEach(function (position) {
            $("#employee_position").append(`
                    <option value=${position.id}>
                        ${position.title}
                    </option>
                `);
        });
        $("#employee_position").val(position.id)
    });
}