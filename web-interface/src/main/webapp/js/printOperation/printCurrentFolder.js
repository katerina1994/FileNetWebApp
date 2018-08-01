import {postJSON} from "../utils/webApi.js";

export async function printCurrentFolder(path) {
    let currentFolder;
    try {
        currentFolder = await postJSON("/hierarchy/operation", {nameOperation: "printCurrentFolder", path: path});
    }catch (e) {
        currentFolder = {
            parent: "No Children"
        };
    }
    const currentFolderNameNode = document.getElementById("printCurrentFolderName");
    const currentFolderChildrenNode = document.getElementById("printChildren");

    currentFolderNameNode.innerText = currentFolder.parent;
    printChild(currentFolder, currentFolderChildrenNode);

}
function printChild(currentFolder, currentFolderChildrenNode){
    while (currentFolderChildrenNode.firstChild) {
        currentFolderChildrenNode.removeChild(currentFolderChildrenNode.firstChild);
    }
    const documentTableNode = document.createElement("table");
    documentTableNode.className = "table table-hover";
    const theadNode = document.createElement("thead");
    const trHeadNode = document.createElement("tr");
    //const childHead = currentFolder.children[0];
    for (let key in currentFolder.children[0]) {
        const documentHeadNode = document.createElement("th");
        documentHeadNode.innerText = key;
        trHeadNode.appendChild(documentHeadNode);
    }
    theadNode.appendChild(trHeadNode);
    documentTableNode.appendChild(theadNode);
    const tbodyNode = document.createElement("tbody");

    for (let child of currentFolder.children) {
        const trBodyNode = document.createElement("tr");
        for (let childKey in child) {
            const documentBodyNode = document.createElement("td");
            documentBodyNode.innerText = child[childKey];
            trBodyNode.appendChild(documentBodyNode);
        }
        tbodyNode.appendChild(trBodyNode);
    }
    documentTableNode.appendChild(tbodyNode);
    currentFolderChildrenNode.appendChild(documentTableNode);
}