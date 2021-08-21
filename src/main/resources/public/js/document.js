function loadDocuments() {
    $.get("/api/documents").done(function (documents) {
        documents.forEach(function (document) {
            $(".content_list").append(`
                <li class="list_item">
                    <a href= "/document/${document.id}" class="list_link">
                    ${document.fileName}
                    </a>
                </li>
            `);
        })
    });

}