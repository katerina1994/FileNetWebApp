package ru.bikert.servlets.Operations.buttonOperation;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import org.json.simple.JSONObject;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.OperationHelper;
import ru.bikert.servlets.Operations.PropertyNameConstant;

public class TrunsferDocumentOperation extends Operation {

    public TrunsferDocumentOperation() {
        super(NamingOperation.TRANSFER, NamingOperation.NameButtonOperation.TRANSFER);
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        String className = (String) jsonObject.get("className");
        String documentName = (String) jsonObject.get("name");
        String path = OperationHelper.getPathFromDocument(className, documentName);
        JSONObject result = new JSONObject();
        try {
            Document document = fileNetManager.getDocument(path);
            String documentStatus = document.getProperties().get(PropertyNameConstant.Property.documentStatus).getStringValue();
            switch (documentStatus) {
                case PropertyNameConstant.DocumentStatus.First:
                    document.getProperties().putValue(PropertyNameConstant.Property.documentStatus, PropertyNameConstant.DocumentStatus.Second);
                    result.put("message", "Document status corrected");
                    break;
                case PropertyNameConstant.DocumentStatus.Second:
                    document.getProperties().putValue(PropertyNameConstant.Property.documentStatus, PropertyNameConstant.DocumentStatus.Third);
                    result.put("message", "Document status corrected");
                    break;
                case PropertyNameConstant.DocumentStatus.Third:
                    result.put("message", "Error Document status " + PropertyNameConstant.DocumentStatus.Third);
                    break;
            }
            document.save(RefreshMode.NO_REFRESH);
            return result;
        }catch (Exception e){
            result.put("message", "Document is Empty");
            return result;
        }
    }
}
