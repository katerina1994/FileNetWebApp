package ru.bikert.servlets.Operations.buttonOperation;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.ReservationType;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import org.json.simple.JSONObject;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.OperationHelper;

import java.io.File;
import java.io.FileInputStream;

public class EditOperation extends Operation {

    public EditOperation() {
        super(NamingOperation.EDIT, NamingOperation.NameButtonOperation.EDIT);
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        String filePath = "D:\\download\\" + jsonObject.get("filePath");
        String documentName = (String) jsonObject.get("name");
        String className = (String) jsonObject.get("className");
        String pathDocument = OperationHelper.getPathFromDocument(className, documentName);
        return editContent(filePath, pathDocument);
    }

    public JSONObject editContent(String pathContent, String pathDocument) {
        JSONObject result = new JSONObject();
        Document doc=fileNetManager.getDocument(pathDocument);
        if (!doc.get_IsReserved()){
            doc.checkout(ReservationType.EXCLUSIVE, null, doc.getClassName(), doc.getProperties());
            doc.save(RefreshMode.REFRESH);
        }
        Document reservation = (Document) doc.get_Reservation();

        // Specify internal and external files to be added as content.
        File internalFile = new File(pathContent);
        // Add content to the Reservation object.
        try {
            // First, add a ContentTransfer object.
            ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
            FileInputStream fileIS = new FileInputStream(internalFile.getAbsolutePath());
            ContentElementList contentList = Factory.ContentTransfer.createList();
            ctObject.setCaptureSource(fileIS);
            // Add ContentTransfer object to list.
            contentList.add(ctObject);

            reservation.set_ContentElements(contentList);
            reservation.save(RefreshMode.REFRESH);
            result.put("message", "File is Edit");
            result.put("type", "success");

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() );
            result.put("message", "Error" + e.getMessage());
            result.put("type", "error");
            return result;
        }
        // Check in Reservation object as major version.
        reservation.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
        reservation.save(RefreshMode.REFRESH);
        return result;
    }
}
