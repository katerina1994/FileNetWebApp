package ru.bikert.servlets.Operations.printOperations;

import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.FolderSet;
import com.filenet.api.core.Document;
import com.filenet.api.core.Folder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;

import java.util.Iterator;

public class GetHierarchyOperation extends Operation {
    private static Logger logger = LogManager.getLogger(GetHierarchyOperation.class);

    public GetHierarchyOperation() {
        super(NamingOperation.PRINTHIERARCHY, "");
    }

    @Override
    public JSONObject performTask(JSONObject json) {
        return getHierarchy(fileNetManager.createRootFolderIfNotExists());
    }

    private JSONObject getHierarchy(Folder folder) {

        JSONObject folderJsonObject = new JSONObject();

        logger.info("get folder from fetchInstance ...");
        logger.info("folder is " + folder.get_FolderName());

        folderJsonObject.put("name", folder.get_FolderName());
        folderJsonObject.put("type", "Folder");
        folderJsonObject.put("path", folder.get_PathName());
        JSONArray children = new JSONArray();
        folderJsonObject.put("children", children);

        FolderSet subFolders = folder.get_SubFolders();
        Iterator it = subFolders.iterator();

        while (it.hasNext()) {
            Folder subFolder = (Folder) it.next();
            children.add(getHierarchy(subFolder));
        }

        DocumentSet documents = folder.get_ContainedDocuments();
        Iterator itDoc = documents.iterator();
        while (itDoc.hasNext()) {
            Document childDocument = (Document) itDoc.next();
            String name = childDocument.get_Name();
            JSONObject childDocumentJsonObject = new JSONObject();
            childDocumentJsonObject.put("name", name);
            childDocumentJsonObject.put("type", "Document");
            childDocumentJsonObject.put("className", childDocument.getClassName());
            children.add(childDocumentJsonObject);
        }
        return folderJsonObject;
    }
}
