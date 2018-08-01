package ru.bikert.servlets.Operations.buttonOperation;

import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.*;
import org.json.simple.JSONObject;
import ru.bikert.servlets.IndexServlet;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.OperationHelper;
import ru.bikert.servlets.Operations.PropertyNameConstant;

import java.util.Date;

public class CreateOperation extends Operation {

    public CreateOperation() {
        super(NamingOperation.CREATE, NamingOperation.NameButtonOperation.СREATE);
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        String className = (String) jsonObject.get("className");
        String documentName = (String) jsonObject.get("name");

        switch (className) {
            case PropertyNameConstant.DocumentClass.ORDER:
                return orderCreate(fileNetManager.getFolder(PropertyNameConstant.FolderPath.ORDER), documentName);
            case PropertyNameConstant.DocumentClass.CONTRACT:
                return contractCreate(fileNetManager.getFolder(PropertyNameConstant.FolderPath.CONTRACT), documentName);
            case PropertyNameConstant.DocumentClass.STATEMENTS:
                return statementsCreate(fileNetManager.getFolder(PropertyNameConstant.FolderPath.STATEMENTS), documentName);
        }
        return new JSONObject();
    }

    private JSONObject orderCreate(Folder folder, String documentName) {
        JSONObject result = new JSONObject();
        String error = errorСhecking(folder, documentName);
        if (error != null){
            result.put("message", error);
            result.put("type", "error");
            return result;
        }

        Document doc = fileNetManager.createDocument(PropertyNameConstant.DocumentClass.ORDER);
        setCommonProperties(documentName, doc);
        doc.save(RefreshMode.NO_REFRESH);

        ReferentialContainmentRelationship drcr = folder.file(
                doc,
                AutoUniqueName.NOT_AUTO_UNIQUE,
                documentName,
                DefineSecurityParentage.DEFINE_SECURITY_PARENTAGE);
        drcr.save(RefreshMode.NO_REFRESH);

        result.put("message", "Order is" + documentName + "create");
        return result;
    }

    private JSONObject contractCreate(Folder folder, String documentName) {
        JSONObject result = new JSONObject();
        String error = errorСhecking(folder, documentName);
        if (error != null){
            result.put("message", error);
            result.put("type", "error");
            return result;
        }
        String counterpartName = "counterparty 1";
        Document doc = fileNetManager.createDocument(PropertyNameConstant.DocumentClass.CONTRACT);
        setCommonProperties(documentName, doc);
        doc.getProperties().putValue(PropertyNameConstant.Property.Counterparty, counterpartName);
        doc.save(RefreshMode.NO_REFRESH);

        ReferentialContainmentRelationship drcr = folder.file(
                doc,
                AutoUniqueName.NOT_AUTO_UNIQUE,
                documentName,
                DefineSecurityParentage.DEFINE_SECURITY_PARENTAGE);
        drcr.save(RefreshMode.NO_REFRESH);

        result.put("message", "Contract is" + documentName + "create");
        return result;
    }

    private JSONObject statementsCreate(Folder folder, String documentName) {
        JSONObject result = new JSONObject();
        String error = errorСhecking(folder, documentName);
        if (error != null){
            result.put("message", error);
            result.put("type", "error");
            return result;
        }
        Document doc = fileNetManager.createDocument(PropertyNameConstant.DocumentClass.STATEMENTS);
        setCommonProperties(documentName, doc);
        doc.getProperties().putValue(PropertyNameConstant.Property.receiptDate, new Date());
        doc.save(RefreshMode.NO_REFRESH);

        ReferentialContainmentRelationship drcr = folder.file(
                doc,
                AutoUniqueName.NOT_AUTO_UNIQUE,
                documentName,
                DefineSecurityParentage.DEFINE_SECURITY_PARENTAGE);
        drcr.save(RefreshMode.NO_REFRESH);
        result.put("message", "Statements is" + documentName + "create");
        return result;
    }

    private Document setCommonProperties(String name, Document doc) {
        doc.getProperties().putValue(PropertyNameConstant.Property.dateApproval, new Date());
        doc.getProperties().putValue(PropertyNameConstant.Property.documentStatus, OperationHelper.get_DocumentStatus(0));
        doc.getProperties().putValue(PropertyNameConstant.Property.numberDocument, OperationHelper.get_EAN13());
        doc.getProperties().putValue(PropertyNameConstant.Property.Responsible, IndexServlet.getCurrentEmployee());
        doc.getProperties().putValue(PropertyNameConstant.Property.DocumentTitle, name);
        return doc;
    }
    private String errorСhecking(Folder folder, String documentName){
       if (fileNetManager.getDocument(folder.get_PathName() + "\\" + documentName) != null)
            return "A uniqueness requirement has been violated.";
        else
            return null;
        }
}
