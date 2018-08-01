import {postJSON} from "../utils/webApi.js";
import {printCurrentFolder} from "./printCurrentFolder.js";

export async function printHierarchy() {
    let rootFolder;
    try {
        rootFolder = await postJSON("/hierarchy/operation", {nameOperation: "getHierarchy"});
    } catch (e) {
        rootFolder = {
            name: "Folder1",
            type: "Folder",
            children: [
                {
                    name: "Folder2",
                    type: "Folder",
                    children: []
                }
            ]
        };
    }
    const rootNode = document.getElementById("hierarchy");
    const headNode = document.getElementById("headingPrint");
    while (rootNode.firstChild) {
        rootNode.removeChild(rootNode.firstChild);
    }
    printRootFolder(rootFolder, headNode);
    printFolderInHierarchy(rootFolder, rootNode, true);
}

function printRootFolder(rootFolder, headNode) {
    headNode.innerText = rootFolder.name;
}

function printFolderInHierarchy(folder, rootNode, isRootFolder) {
    const folderNode = document.createElement("div");
    if (!isRootFolder) {
        const folderNameNode = document.createElement("div");
        folderNameNode.innerText = folder.name;
        folderNode.appendChild(folderNameNode);
    }

    const folderChildrenNode = document.createElement("ul");
    folderNode.appendChild(folderChildrenNode);
    for (let child of folder.children) {
        const childNode = document.createElement("li");
        childNode.addEventListener("click", () => printCurrentFolder(child.path));
        folderChildrenNode.appendChild(childNode);
        if (child.type === "Folder") {
            printFolderInHierarchy(child, childNode);
        } else {
            printDocumentInHierarchy(child, childNode);
        }

    }
    rootNode.appendChild(folderNode);
}

function printDocumentInHierarchy(doc, rootNode) {
    const documentNode = document.createElement("div");

    const documentNameNode = document.createElement("div");
    documentNameNode.innerText = doc.name;
    documentNode.appendChild(documentNameNode);


    rootNode.appendChild(documentNode);
}
