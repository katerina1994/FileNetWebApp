package ru.bikert.servlets.Operations.buttonOperation;

import org.json.simple.JSONObject;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.PropertyNameConstant;

public class DeleteOperation extends Operation {

    public DeleteOperation() {
        super(NamingOperation.DELETE, NamingOperation.NameButtonOperation.DELETE);
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        String className = (String) jsonObject.get("className");
        String documentName = (String) jsonObject.get("name");
        String path = null;
        JSONObject result = new JSONObject();
        switch (className) {
            case PropertyNameConstant.DocumentClass.ORDER:
                path = PropertyNameConstant.FolderPath.ORDER + "/" + documentName ;
                break;
            case PropertyNameConstant.DocumentClass.CONTRACT:
                path = PropertyNameConstant.FolderPath.CONTRACT + "/" + documentName;
                break;
            case PropertyNameConstant.DocumentClass.STATEMENTS:
                path = PropertyNameConstant.FolderPath.STATEMENTS + "/" + documentName;
                break;
        }
        if(fileNetManager.removeDocument(path)){
            result.put("message", "Document " + documentName + " is deleted");
            return result;
        }
        result.put("message", "Error");
        result.put("type", "error");
        return result;
    }
}
