import {postJSON} from "../utils/webApi.js";

export async function reportOperation() {
    let rootFolder;
    try {
        rootFolder = await postJSON("/hierarchyold/operation", {nameOperation: "report"});
    } catch (e) {
        rootFolder = {"children": [
                {"dateApproval":"30.07.2018","name":"Document1","documentStatus":"Утвержден","numberDocument":4.69088275E8,"class":"Contract"},
                {"dateApproval":"30.07.2018","name":"Document1","documentStatus":"На ознакомлении","numberDocument":4.69098856E8,"class":"Statements"},
                {"dateApproval":"30.07.2018","name":"Document55","documentStatus":"На ознакомлении","numberDocument":4.69074714E8,"class":"Statements"},
                {"dateApproval":"30.07.2018","name":"hhhh","documentStatus":"На ознакомлении","numberDocument":4.69095978E8,"class":"Statements"},
                {"dateApproval":"30.07.2018","name":"Document1","documentStatus":"На ознакомлении","numberDocument":4.69090116E8,"class":"Order"},
                {"dateApproval":"23.07.2018","name":"Order5","documentStatus":"Утвержден","numberDocument":4.69061829E8,"class":"Order"},
                {"dateApproval":"23.07.2018","name":"Order6","documentStatus":"Утвержден","numberDocument":4.69087873E8,"class":"Order"},
                {"dateApproval":"30.07.2018","name":"hhhhhhh","documentStatus":"На ознакомлении","numberDocument":4.69062261E8,"class":"Order"}
                ]};
    }
    const rootNode = document.getElementById("report");
    const headNode = document.getElementById("headingReportPrint");

    printReport(rootFolder, rootNode);
}
function printReport(rootFolder, rootNode) {

    while (rootNode.firstChild) {
        rootNode.removeChild(rootNode.firstChild);
    }
    const documentTableNode = document.createElement("table");
    documentTableNode.className = "table table-hover";
    const theadNode = document.createElement("thead");
    const trHeadNode = document.createElement("tr");
    for (let key in rootFolder.children[0]) {
        const documentHeadNode = document.createElement("th");
        documentHeadNode.innerText = key;
        trHeadNode.appendChild(documentHeadNode);
    }
    theadNode.appendChild(trHeadNode);
    documentTableNode.appendChild(theadNode);
    const tbodyNode = document.createElement("tbody");

    for (let child of rootFolder.children) {
        const trBodyNode = document.createElement("tr");
        for (let childKey in child) {
            const documentBodyNode = document.createElement("td");
            documentBodyNode.innerText = child[childKey];
            trBodyNode.appendChild(documentBodyNode);
        }
        tbodyNode.appendChild(trBodyNode);
    }
    documentTableNode.appendChild(tbodyNode);
    rootNode.appendChild(documentTableNode);
}