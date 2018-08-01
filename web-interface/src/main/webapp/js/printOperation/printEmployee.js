import {postJSON} from "../utils/webApi.js";

export async function replaceEmployee() {
    let currentEmployee;
    try {
        currentEmployee = await postJSON("/hierarchy/operation", {nameOperation: "printCurrentEmployee"});
    }catch (e) {
    }
    printEmployee(currentEmployee);
}
function printEmployee(employee) {
    const employeeNode = document.getElementById("employee");
    const printNode = document.createElement("div");
    printNode.innerText = employee.name;
    employeeNode.appendChild(printNode);
}