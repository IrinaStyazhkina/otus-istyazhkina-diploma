function loadAllContractors() {
    $.get("/api/contractors").done(function (companies) {
        companies.forEach(function (company) {
            $("tbody").append(`
                <tr class="table_row">
                    <td class="table_cell">${company.id}</td>
                    <td class="table_cell">${company.companyLegalForm.title}</td>
                    <td class="table_cell">${company.companyName}</td>
                    <td class="table_cell">${company.managerPosition.title}</td>
                    <td class="table_cell">${company.managerName}</td>
                    <td class="table_cell">${company.managerSecondName}</td>
                    <td class="table_cell">${company.managerSurname}</td>
                    <td class="table_cell">
                        <a class="table_edit-link" href="/contractor/${company.id}">Просмотр</a>
                    </td>
                    <td class="table_cell">
                        <button onclick="deleteContractor('${company.id}')" class="delete_button" type="button">Удалить</button>
                    </td>
                </tr>
            `);
        })
    });
}

function loadContractor() {
    const path = window.location.pathname;
    const pathLastPart = path.substr(path.lastIndexOf("/") + 1);
    if (pathLastPart === "add") {
        $(".form_wrapper").append(`
            <form action="/contractor/add" class="item_form" id="add-contractor" method="post" onsubmit="addContractor(); return false">
                    <label class="item_label" for="company_form">Организациионно-правовая форма</label>
                    <select class="item_select" id="company_form">
                    </select>
                    <label class="item_label" for="company_name">Название компании</label>
                    <input class="item_input" id="company_name" value="" required>
                    <label class="item_label" for="company_inn">ИНН</label>
                    <input class="item_input" id="company_inn" value="" required>
                    <label class="item_label" for="manager_position">Должность управляющего</label>
                    <select class="item_select" id="manager_position">
                    </select>
                    <label class="item_label" for="manager_name">Имя управляющего</label>
                    <input class="item_input" id="manager_name" value="" required>
                    <label class="item_label" for="manager_secondName">Отчество управляющего</label>
                    <input class="item_input" id="manager_secondName" value="">
                    <label class="item_label" for="manager_surname">Фамилия управляющего</label>
                    <input class="item_input" id="manager_surname" value="" required>
                    <button class="item_btn" type="submit" id="save">Сохранить</button>
                </form>
        `);
        getCompanyLegalForms();
        getManagerPositions();
    } else {
        $.get("/api/contractor/" + pathLastPart).done(function (company) {
            $(".form_wrapper").append(`
                <form class="item_form" id="edit-contractor" action="/contractor/${pathLastPart}" method="put" name="contractor" onsubmit="updateContractor(); return false;">
                    <label class="item_label" for="company_id">ID</label>
                    <input class="item_input book_input--disabled" id="company_id"  value="${company.id}" disabled>
                    <label class="item_label" for="company_form">Организационно-правовая форма</label>
                    <select class="item_select" id="company_form">
                    </select>
                    <label class="item_label" for="company_name">Название компании</label>
                    <input class="item_input" id="company_name" value="${company.companyName}" required>
                    <label class="item_label" for="company_inn">ИНН</label>
                    <input class="item_input" id="company_inn" value="${company.inn}" required>
                    <label class="item_label" for="manager_position">Должность управляющего</label>
                    <select class="item_select" id="manager_position">
                    </select>
                    <label class="item_label" for="manager_name">Имя управляющего</label>
                    <input class="item_input" id="manager_name" value="${company.managerName}" required>
                    <label class="item_label" for="manager_secondName">Отчество управляющего</label>
                    <input class="item_input" id="manager_secondName" value="${company.managerSecondName}">
                    <label class="item_label" for="manager_surname">Фамилия управляющего</label>
                    <input class="item_input" id="manager_surname" value="${company.managerSurname}" required>
                    <button class="item_btn" type="submit" id="save">Сохранить</button>
                </form> 
            `);
            getCompanyLegalFormsAndSelect(company.companyLegalForm);
            getManagerPositionsAndSelect(company.managerPosition);
        });
    }
}

function addContractor() {
    const form = $("#add-contractor");
    const url = form.attr("action");
    const companyLegalFormId = document.querySelector("#company_form").value;
    const companyName = document.querySelector("#company_name").value;
    const managerPositionId = document.querySelector("#manager_position").value;
    const managerName = document.querySelector("#manager_name").value;
    const managerSecondName = document.querySelector("#manager_secondName").value;
    const managerSurname = document.querySelector("#manager_surname").value;
    const inn = document.querySelector("#company_inn").value;
    const contractor = {
        'id': null,
        'companyLegalForm': {'id': companyLegalFormId},
        'companyName': companyName,
        'managerPosition': {'id': managerPositionId},
        'managerName': managerName,
        'managerSecondName': managerSecondName,
        'managerSurname': managerSurname,
        'inn': inn
    };

    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(contractor),
        contentType: "application/json;charset=utf-8",
        success: function () {
            alert("Контрагент успешно добавлен!");
            goToContractorsPage();
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

function updateContractor() {
    const form = $("#edit-contractor");
    const url = form.attr("action");
    const id = document.querySelector("#company_id").value;
    const companyLegalFormId = document.querySelector("#company_form").value;
    const companyName = document.querySelector("#company_name").value;
    const managerPositionId = document.querySelector("#manager_position").value;
    const managerName = document.querySelector("#manager_name").value;
    const managerSecondName = document.querySelector("#manager_secondName").value;
    const managerSurname = document.querySelector("#manager_surname").value;
    const inn = document.querySelector("#company_inn").value;
    const contractor = {
        'id': id,
        'companyLegalForm': {'id': companyLegalFormId},
        'companyName': companyName,
        'managerPosition': {'id': managerPositionId},
        'managerName': managerName,
        'managerSecondName': managerSecondName,
        'managerSurname': managerSurname,
        'inn': inn
    };

    $.ajax({
        url: url,
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(contractor),
        contentType: "application/json;charset=utf-8",
        success: function () {
            alert("Контрагент успешно обновлен!");
            goToContractorsPage();
        },
        error: function (error) {
            alert(error.responseText);
        }
    });

}


function deleteContractor(companyId) {
    $.ajax({
        url: "/contractor/" + companyId,
        method: "DELETE",
        success: function () {
            alert("Контрагент с ID " + companyId + " успешно удален!");
            goToContractorsPage();
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

function goToContractorsPage() {
    location.href = "/contractor/all";
}

function getCompanyLegalForms() {
    $.get("/api/legal_forms").done(function (legalForms) {
        legalForms.forEach(function (legalForm) {
            $("#company_form").append(`
                    <option value=${legalForm.id}>
                        ${legalForm.title}
                    </option>
                `);
        });
    });
}

function getCompanyLegalFormsAndSelect(companyLegalForm) {
    $.get("/api/legal_forms").done(function (legalForms) {
        legalForms.forEach(function (legalForm) {
            $("#company_form").append(`
                    <option value=${legalForm.id}>
                        ${legalForm.title}
                    </option>
                `);
        });
        $("#company_form").val(companyLegalForm.id)
    });
}

function getManagerPositions() {
    $.get("/api/manager_positions").done(function (managerPositions) {
        managerPositions.forEach(function (managerPosition) {
            $("#manager_position").append(`
                    <option value=${managerPosition.id}>
                        ${managerPosition.title}
                    </option>
                `);
        });
    });
}

function getManagerPositionsAndSelect(managerPosition) {
    $.get("/api/manager_positions").done(function (managerPositions) {
        managerPositions.forEach(function (managerPosition) {
            $("#manager_position").append(`
                    <option value=${managerPosition.id}>
                        ${managerPosition.title}
                    </option>
                `);
        });
        $("#manager_position").val(managerPosition.id)
    });
}
