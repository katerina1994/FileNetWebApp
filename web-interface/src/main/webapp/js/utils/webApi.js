function handleResponse(response) {

    if (response.ok) {
        return response;
    }

    const { status } = response;

    const readResponseFn =
        response.json || response.text ||
        (() => Promise.reject("No function to read reponse"));

    return readResponseFn()
        .catch((e) => Promise.reject({
            statusCode: status,
            body: `Unhandled error: ${JSON.stringify(e)}`
        }))
        .then((body) => Promise.reject({
            statusCode: status, body
        }));

}

function ajax(url, method, data) {
    const options = {
        method,
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json"
        }
    };

    if (data) {
        options.body = JSON.stringify(data);
    }

    return fetch(url, options)
        .then(handleResponse)
        .then((response) => response.json());

}

export function postJSON(url, data) {
    return ajax(url, "POST", data);
}
export async function postForm(url, formData) {
   const response = await fetch(url , {
       method: "POST",
       body: formData
   });
    if (response.ok) {
       return response.json();
    }
    // const { status } = response;
    // throw new Error(`Status ${status}`);
}


export function putJSON(url, data) {
    return ajax(url, "PUT", data);
}

export function getJSON(url) {
    return ajax(url, "GET");
}

export function delJSON(url) {
    return ajax(url, "DELETE");
}

export function sendAjaxForm(url, form) {

    let body = $("#"+form).serialize();
    $.ajax({
        url, //url страницы (action_ajax_form.php)
        type:     "POST", //метод отправки
        dataType: "html", //формат данных
        data: body,
        success: function(response) { //Данные отправлены успешно
            const operationDialogBody = document.getElementById("operationDialogBody");
            const message = document.createElement("div");
            message.className = "alert alert-success";
            message.innerText = (JSON.parse(response)).message;
            operationDialogBody.appendChild(message);
        },
        error: function(response) { // Данные не отправлены
            const operationDialogBody = document.getElementById("operationDialogBody");
            const message = document.createElement("div");
            message.className = "alert alert-danger";
            message.innerText = "Error" + response;
            operationDialogBody.appendChild(message);
        }
    });
}