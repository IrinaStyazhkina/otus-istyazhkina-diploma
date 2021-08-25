function loadData() {
    const path = window.location.pathname;
    const templateId = path.substr(path.lastIndexOf("/") + 1);
    if (templateId == 2) {
        $(".form_wrapper").append(`
            <form action="/doc/create/${templateId}" class="item_form" id="add-formdata" method="post" onsubmit="getOrderPdf(); return false">
                    <label class="item_label" for="company">Укажите компанию, в которой работает сотрудник:</label>
                    <select class="item_select" id="company">
                    </select>
                     <label class="item_label" for="employee">Укажите сотрудника:</label>
                    <select class="item_select" id="employee">
                    </select>
                    <label class="item_label" for="city">Город</label>
                    <input class="item_input" id="city" value="" required>
                    <label class="item_label" for="order_date">Дата приказа:</label>
                    <input class="item_input" id="order_date" value="" required> 
                    <label class="item_label" for="salary_rub">Новая заработная плата (рубли)</label>
                    <input class="item_input" id="salary_rub" value="">
                    <label class="item_label" for="salary_cop">Новая заработная плата (копейки)</label>
                    <input class="item_input" id="salary_cop" value="">
                    <label class="item_label" for="salary_date">Дата начала начисления новой заработной платы</label>
                    <input class="item_input" id="salary_date" value="" required> 
                    <button class="item_btn" type="submit" id="save">
                        <img src="/img/pdf_icon.png" height="35" width="35"/>
                        Получить файл
                    </button>
                </form>
        `);
    } else if (templateId == 1) {
        $(".form_wrapper").append(`
            <form action="/doc/create/${templateId}" class="item_form" id="add-formdata" method="post" onsubmit="getPoAPdf(); return false">
                    <label class="item_label" for="company">Укажите компанию, от имени которой будет выдана доверенность:</label>
                    <select class="item_select" id="company">
                    </select>
                     <label class="item_label" for="employee">Укажите сотрудника, на которого выдается доверенность:</label>
                    <select class="item_select" id="employee">
                    </select>
                    <label class="item_label" for="city">Город</label>
                    <input class="item_input" id="city" value="" required>
                    <label class="item_label" for="doc_date">Дата доверенности:</label>
                    <input class="item_input" id="doc_date" value="" required> 
                    <label class="item_label" for="powers">Укажите цель доверенности:</label>
                    <select class="item_select" id="powers">
                    </select>
                    <label class="item_label" for="expires">Доверенность действитальна до:</label>
                    <input class="item_input" id="expires" value="">
                
                    <button class="item_btn" type="submit" id="save">
                        <img src="/img/pdf_icon.png" height="35" width="35"/>
                        Получить файл
                    </button>
                </form>
        `);
        getPowers();
    }
    getCompanies();
    getEmployees();
}

function getPdf(formData) {
    const form = $("#add-formdata");
    const url = form.attr("action");

    var res;
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(formData),
        async: false,
        contentType: "application/json;charset=utf-8",
        success: function (response) {
            res = response.link;
            $(".form_wrapper").append(`
                 <a class="pdf_btn" href="/download-pdf/${res}">
                    <span>Скачать PDF</span>
                  </a>
                  <button class="pdf_btn" onclick="saveDocument(\'${res}\');">Сохранить и скачать позже</button>
            `);
        },
        error: function (error) {
            alert(error.responseText);
        }
    });
}

function saveDocument(link) {
    const data = {
        'link': link
    };
    $.ajax({
        url: "/save",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        async: false,
        contentType: "application/json;charset=utf-8",
        complete: function () {
            alert("Документ успешно добавлен!")
        }
    });
}

function getOrderPdf() {
    const companyId = document.querySelector("#company").value;
    const employeeId = document.querySelector("#employee").value;
    const city = document.querySelector("#city").value;
    const orderDate = document.querySelector("#order_date").value;
    const salaryRub = document.querySelector("#salary_rub").value;
    const salaryCop = document.querySelector("#salary_cop").value;
    const salaryDate = document.querySelector("#salary_date").value;

    const formData = {
        'company': {'id': companyId},
        'employee': {'id': employeeId},
        'city': city,
        'docDate': orderDate,
        'salaryRub': salaryRub,
        'salaryCop': salaryCop,
        'salaryDate': salaryDate
    };
    getPdf(formData);
}

function getPoAPdf() {
    const companyId = document.querySelector("#company").value;
    const employeeId = document.querySelector("#employee").value;
    const city = document.querySelector("#city").value;
    const docDate = document.querySelector("#doc_date").value;
    const power_id = document.querySelector("#powers").value;
    const expireDate = document.querySelector("#expires").value;

    const formData = {
        'company': {'id': companyId},
        'employee': {'id': employeeId},
        'city': city,
        'docDate': docDate,
        'expireDate': expireDate,
        'power': {'id': power_id}
    };
    getPdf(formData);
}

function getCompanies() {
    $.get("/api/contractors").done(function (contractors) {
        contractors.forEach(function (contractor) {
            $("#company").append(`
                    <option value=${contractor.id}>
                        ${contractor.companyLegalForm.title} ${contractor.companyName}
                    </option>
                `);
        });
    });
}

function getEmployees() {
    $.get("/api/employees").done(function (employees) {
        employees.forEach(function (employee) {
            $("#employee").append(`
                    <option value=${employee.id}>
                        ${employee.name} ${employee.secondName} ${employee.surname}
                    </option>
                `);
        })
    });
}

function getPowers() {
    $.get("/api/powers").done(function (powers) {
        powers.forEach(function (power) {
            $("#powers").append(`
                    <option value=${power.id}>
                        ${power.name}
                    </option>
                `);
        })
    });

}



