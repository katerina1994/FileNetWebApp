package ru.bikert.servlets.Operations;

import net.sf.jasperreports.engine.JRException;
import org.json.simple.JSONObject;
import ru.bikert.filenet.FileNetManager;


public abstract class Operation {

    String nameOperation;
    String buttonTitle;
    protected FileNetManager fileNetManager;

    public Operation(String nameOperation, String buttonTitle) {
        this.nameOperation = nameOperation;
        this.buttonTitle = buttonTitle;
        this.fileNetManager = new FileNetManager();
    }

    public abstract JSONObject performTask(JSONObject jsonObject);

    public String getNameOperation() {
        return nameOperation;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

}
