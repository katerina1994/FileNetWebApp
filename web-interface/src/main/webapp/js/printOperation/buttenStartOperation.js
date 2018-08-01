import {reportOperation} from "./printReport.js";
import {printHierarchy} from "./printHierarchy.js";
import {postJSON, sendAjaxForm, postForm} from "../utils/webApi.js";

export async function printButtonOperation() {
    let buttonList;
    try {
        buttonList = await postJSON("/hierarchy/operation", {nameOperation: "printButtonList"});
    } catch (e) {

    }
    const rootButtonNode = document.getElementById("buttonOperations");
    for (let button of buttonList.buttons) {
        if(button.nameOperation === "report"){
            const buttonNode = document.createElement("button");
            buttonNode.className = "btn btn-success";
            buttonNode.addEventListener("click", () => reportOperation(button.nameOperation));
            buttonNode.innerText = button.buttonTitle;
            rootButtonNode.appendChild(buttonNode);
        } else if(button.nameOperation === "edit"){
            const buttonNode = document.createElement("button");
            buttonNode.className = "btn btn-success";
            buttonNode.type = "button";
            buttonNode.setAttribute("data-toggle", "modal");
            buttonNode.setAttribute("data-target", "#operationDialogFile");
            buttonNode.addEventListener("click", () => openModalFile(this, button.nameOperation));
            buttonNode.innerText = button.buttonTitle;
            rootButtonNode.appendChild(buttonNode);
        }
        else {
            const buttonNode = document.createElement("button");
            buttonNode.className = "btn btn-success";
            buttonNode.type = "button";
            buttonNode.setAttribute("data-toggle", "modal");
            buttonNode.setAttribute("data-target", "#operationDialog");
            buttonNode.addEventListener("click", () => openModal(this, button.nameOperation));
            buttonNode.innerText = button.buttonTitle;
            rootButtonNode.appendChild(buttonNode);
        }
    }
}

function openModal(button, action) {
    var name = button.innerText;
    const operationDialogHeader = document.getElementById("operationDialogHeader");
    operationDialogHeader.innerText = name;
    const inputForm = document.getElementById("inputForm");
    const inputNameOperationHidden = document.createElement("input");
    inputNameOperationHidden.type = "hidden";
    inputNameOperationHidden.setAttribute("name", "nameOperation");
    inputNameOperationHidden.setAttribute("value", action);
    inputForm.appendChild(inputNameOperationHidden);
    const inputPathHidden = document.createElement("input");
    inputPathHidden.type = "hidden";
    inputPathHidden.setAttribute("name", "path");
    inputPathHidden.setAttribute("value", action);
    inputForm.appendChild(inputPathHidden);
    const submitForm = document.getElementById("submitForm");
    submitForm.addEventListener("click", () => formSend("operationForm", "resultOperation"));
}

function openModalFile(button, action) {
    var name = button.innerText;
    var employee = document.getElementById("employee").innerText;
    const operationDialogHeader = document.getElementById("operationDialogFileHeader");
    operationDialogHeader.innerText = name;
    const inputForm = document.getElementById("operationFileForm");
    const divGroupNode = document.createElement("div");
    divGroupNode.className = "form-group";
    const inputNameOperationHidden = document.createElement("input");
    inputNameOperationHidden.type = "hidden";
    inputNameOperationHidden.setAttribute("name", "nameOperation");
    inputNameOperationHidden.setAttribute("value", action);
    divGroupNode.appendChild(inputNameOperationHidden);
    inputForm.appendChild(divGroupNode);
    const inputPathHidden = document.createElement("input");
    inputPathHidden.type = "hidden";
    inputPathHidden.setAttribute("name", "employee");
    inputPathHidden.setAttribute("value", employee);
    divGroupNode.appendChild(inputPathHidden);
    inputForm.appendChild(divGroupNode);
    const submitFileForm = document.getElementById("submitFileForm");
    submitFileForm.addEventListener("click", () => formSend("operationFileForm", "resultFileOperation"));
}

async function formSend(form, resultElementId){
    var formNode = document.getElementById(form);
    const nodeResult = document.getElementById(resultElementId);
    printResult(nodeResult, await postForm("/hierarchy/operation", new FormData(formNode)));
    await printHierarchy();
}

function printResult(nodeResult, json) {
    const message = document.createElement("div");
    if (json.type === "error"){
        message.className = "alert alert-danger";
    } else message.className = "alert alert-success";

    message.innerText = json.message;
    nodeResult.appendChild(message);
}
