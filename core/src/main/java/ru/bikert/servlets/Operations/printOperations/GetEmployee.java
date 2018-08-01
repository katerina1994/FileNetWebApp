package ru.bikert.servlets.Operations.printOperations;

import org.json.simple.JSONObject;
import ru.bikert.servlets.IndexServlet;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.PropertyNameConstant;
import com.filenet.api.core.*;

public class GetEmployee extends Operation {

    public GetEmployee() {
        super("", "");
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        int i = 0 + ((int) (Math.random() * 2));
        CustomObject customObject = fileNetManager.getEmployee(PropertyNameConstant.Employee.nameEmloyee[i]);
        IndexServlet.setCurrentEmployee(customObject);
        JSONObject result = new JSONObject();
        result.put("name", customObject.getProperties().get("fullName").getStringValue());
        return result;
    }
}
