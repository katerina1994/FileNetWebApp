package ru.bikert.servlets.Operations.printOperations;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.meta.ClassDescription;
import com.filenet.api.property.Properties;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.Id;
import net.sf.jasperreports.engine.JRException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.bikert.report.PrintReport;
import ru.bikert.servlets.NamingOperation;
import ru.bikert.servlets.Operations.Operation;
import ru.bikert.servlets.Operations.OperationHelper;
import ru.bikert.servlets.Operations.PropertyNameConstant;

import java.text.SimpleDateFormat;
import java.util.Iterator;

public class GetReportThroughSQL extends Operation {


    public GetReportThroughSQL() {
        super(NamingOperation.REPORT,NamingOperation.NameButtonOperation.REPORT);
    }

    @Override
    public JSONObject performTask(JSONObject jsonObject) {
        String query = "SELECT This DocumentTitle, MajorVersionNumber, MinorVersionNumber, dateApproval, documentStatus, numberDocument, Responsible1 FROM Document WHERE This INSUBFOLDER '/Программа адаптации/'\n";
        IndependentObjectSet objects = fileNetManager.getObjectSetThroughSQL(query);

        JSONObject documentJsonObject = new JSONObject();
        JSONArray children = new JSONArray();

        Iterator iterator = objects.iterator();

        while (iterator.hasNext()) {
            Document document = (Document)iterator.next();
            document.fetchProperties(new String[] {"DocumentTitle"});
            Properties properties = document.getProperties();
            JSONObject child = getValueOfDocumentPropertiesWithSQL(properties);
            children.add(child);
        }

        documentJsonObject.put("children", children);
        PrintReport.printReport(children);
        return documentJsonObject;
    }

    public static JSONObject getValueOfDocumentPropertiesWithSQL(Properties properties){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        JSONObject child = new JSONObject();
        child.put("MajorVersion", properties.getInteger32Value("MajorVersionNumber"));
        child.put("MinorVersion", properties.getInteger32Value("MinorVersionNumber"));
        child.put("name", properties.getStringValue("DocumentTitle"));
        child.put(PropertyNameConstant.Property.dateApproval, dateFormat.format(properties.getDateTimeValue(PropertyNameConstant.Property.dateApproval)));
        child.put(PropertyNameConstant.Property.documentStatus, properties.getStringValue(PropertyNameConstant.Property.documentStatus));
        child.put(PropertyNameConstant.Property.numberDocument, properties.getFloat64Value(PropertyNameConstant.Property.numberDocument));

        if (properties.getObjectValue(PropertyNameConstant.Property.Responsible) !=null) {
            CustomObject employee = (CustomObject) properties.getObjectValue(PropertyNameConstant.Property.Responsible);
            String nameEmployee = employee.getProperties().get("FullName").getStringValue();
            child.put(PropertyNameConstant.Property.Responsible, nameEmployee);
        } else child.put(PropertyNameConstant.Property.Responsible, "null");
        return child;
    }
}
