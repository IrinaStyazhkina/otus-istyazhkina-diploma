function loadTemplates() {
    $.get("/api/template").done(function (documentTypeDTOs) {
        documentTypeDTOs.forEach(function (documentTypeDTO) {
            $(".content_list").append(`
                <li class="list_item">
                    <a href= "/form/${documentTypeDTO.id}" class="list_link">
                    ${documentTypeDTO.alias}
                    </a>
                </li>
            `);
        })
    });

}
