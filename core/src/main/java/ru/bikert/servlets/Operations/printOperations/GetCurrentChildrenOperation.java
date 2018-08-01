package ru.bikert.servlets.Operations.printOperations;

import com.filenet.api.collection.DocumentSet;
import com.filenet.api.core.Document;
import com.filenet.api.core.Folder;
import com.filenet.api.core.IndependentObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.PropertyNameConstant;

import java.text.SimpleDateFormat;
import java.util.Iterator;

public class GetCurrentChildrenOperation extends Operation {

    public GetCurrentChildrenOperation() {
        super(NamingOperation.CURRENTCHILDREN, "");
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        JSONObject result = new JSONObject();
        String path = (String) jsonObject.get("path");
        Folder currentFolder = fileNetManager.getFolder(path);

        result.put("parent", currentFolder.get_FolderName());
        result.put("path", currentFolder.get_PathName());
        JSONArray children = new JSONArray();
        DocumentSet documents = currentFolder.get_ContainedDocuments();
        Iterator itDoc = documents.iterator();
        while(itDoc.hasNext()) {
            Document retrieveDoc = (Document) itDoc.next();
            IndependentObject employee = (IndependentObject)retrieveDoc.getProperties().get(PropertyNameConstant.Property.Responsible).getObjectValue();
            JSONObject child = new JSONObject();
            if (employee != null){
                employee.getProperties().get("FullName").getStringValue();
                child.put(PropertyNameConstant.Property.Responsible, employee.getProperties().get("FullName").getStringValue());
            } else child.put(PropertyNameConstant.Property.Responsible, "null");
            if(retrieveDoc.getClassName().equals(PropertyNameConstant.DocumentClass.CONTRACT)){
                child.put(PropertyNameConstant.Property.Counterparty, retrieveDoc.getProperties().get(PropertyNameConstant.Property.Counterparty).getStringValue());
            }
            if (retrieveDoc.getClassName().equals(PropertyNameConstant.DocumentClass.STATEMENTS)){
                child.put(PropertyNameConstant.Property.receiptDate, dateFormat.format(retrieveDoc.getProperties().get(PropertyNameConstant.Property.receiptDate).getDateTimeValue()));
            }
            child.put("name", retrieveDoc.get_Name());
            child.put("class", retrieveDoc.getClassName());
            child.put(PropertyNameConstant.Property.dateApproval, dateFormat.format(retrieveDoc.getProperties().get(PropertyNameConstant.Property.dateApproval).getDateTimeValue()));
            child.put(PropertyNameConstant.Property.documentStatus, retrieveDoc.getProperties().get(PropertyNameConstant.Property.documentStatus).getStringValue());
            child.put(PropertyNameConstant.Property.numberDocument, retrieveDoc.getProperties().get(PropertyNameConstant.Property.numberDocument).getFloat64Value());
            children.add(child);
        }
        result.put("children", children);
        return result;
    }
}
