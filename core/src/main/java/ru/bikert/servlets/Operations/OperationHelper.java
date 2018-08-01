package ru.bikert.servlets.Operations;

import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class OperationHelper {

    private static List<String> documentStatus = new LinkedList<String>();

    public static long get_EAN13 (){
        return 469000000 + ((int)(Math.random() * 100000));
    }

    public static String get_DocumentStatus(int i){
        documentStatus.add("На ознакомлении");
        documentStatus.add("На утверждении");
        documentStatus.add("Утвержден");
        return documentStatus.get(i);
    }
    public static String getPathFromDocument(String className, String documentName){
        switch (className) {
            case PropertyNameConstant.DocumentClass.ORDER:
                return PropertyNameConstant.FolderPath.ORDER + "/" + documentName ;
            case PropertyNameConstant.DocumentClass.CONTRACT:
                return PropertyNameConstant.FolderPath.CONTRACT + "/" + documentName;
            case PropertyNameConstant.DocumentClass.STATEMENTS:
                return PropertyNameConstant.FolderPath.STATEMENTS + "/" + documentName;
        }
        return null;
    }
    public static JSONObject getValueOfDocumentProperties(Document doc){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        JSONObject child = new JSONObject();
        child.put("MajorVersion", doc.getProperties().get("MajorVersionNumber").getInteger32Value());
        child.put("MinorVersion", doc.getProperties().get("MinorVersionNumber").getInteger32Value());
        child.put("name", doc.get_Name());
        child.put("class", doc.getClassName());
        child.put(PropertyNameConstant.Property.dateApproval, dateFormat.format(doc.getProperties().get(PropertyNameConstant.Property.dateApproval).getDateTimeValue()));
        child.put(PropertyNameConstant.Property.documentStatus, doc.getProperties().get(PropertyNameConstant.Property.documentStatus).getStringValue());
        child.put(PropertyNameConstant.Property.numberDocument, doc.getProperties().get(PropertyNameConstant.Property.numberDocument).getFloat64Value());

        if (doc.getProperties().get(PropertyNameConstant.Property.Responsible).getObjectValue() !=null) {
            CustomObject employee = (CustomObject) doc.getProperties().get(PropertyNameConstant.Property.Responsible).getObjectValue();
            String nameEmployee = employee.getProperties().get("FullName").getStringValue();
            child.put(PropertyNameConstant.Property.Responsible, nameEmployee);
        } else child.put(PropertyNameConstant.Property.Responsible, "null");
        return child;
    }
}
