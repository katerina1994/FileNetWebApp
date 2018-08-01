package ru.bikert.servlets.Operations.buttonOperation;

import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.FolderSet;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Folder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.OperationHelper;
import ru.bikert.servlets.Operations.PropertyNameConstant;

import java.text.SimpleDateFormat;
import java.util.Iterator;

public class ReportOperation extends Operation {

    public ReportOperation() {
        super(NamingOperation.REPORT,NamingOperation.NameButtonOperation.REPORT);
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        return getDocuments(fileNetManager.createRootFolderIfNotExists());
    }

    private JSONObject getDocuments(Folder folder) {

        JSONObject documentJsonObject = new JSONObject();
        JSONArray children = new JSONArray();

        FolderSet subFolders = folder.get_SubFolders();
        Iterator it = subFolders.iterator();

        while (it.hasNext()) {
            Folder subFolder = (Folder) it.next();
            DocumentSet documents = subFolder.get_ContainedDocuments();
            Iterator itDoc = documents.iterator();
            while (itDoc.hasNext()) {
                Document childDocument = (Document) itDoc.next();
                JSONObject child = OperationHelper.getValueOfDocumentProperties(childDocument);
                children.add(child);
            }
        }
        documentJsonObject.put("children", children);
        return documentJsonObject;
    }

}
