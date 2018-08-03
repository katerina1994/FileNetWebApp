package ru.bikert.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.bikert.servlets.Operations.PropertyNameConstant;


public class JsonToDataSource implements JRDataSource {
    int index = 0;
    JSONArray data;

    public JsonToDataSource(JSONArray data) {
        this.data = data;
    }

    @Override
    public boolean next() throws JRException {
        if (index < data.size()-1){
            index++;
            return true;
        } return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        JSONObject object = (JSONObject) data.get(index);
        switch (jrField.getName()){
            case "DOCUMENT_TITLE":  return object.get("name");
            case "DATE_APPROVAL":   return object.get(PropertyNameConstant.Property.dateApproval);
            case "RESPONSIBLE":     return object.get(PropertyNameConstant.Property.Responsible);
            case "DOCUMENT_STATUS": return object.get(PropertyNameConstant.Property.documentStatus);
            case "NUMBER_DOCUMENT": return object.get(PropertyNameConstant.Property.numberDocument).toString();
            case "MAJOR_VERSION":   return object.get("MajorVersion").toString();
            case "MINOR_VERSION":   return object.get("MinorVersion").toString();
        }
        return null;
    }

    public static String[] fieldNames() {
        String[] fieldNames = {"DOCUMENT_TITLE", "DATE_APPROVAL", "RESPONSIBLE", "DOCUMENT_STATUS", "NUMBER_DOCUMENT", "MAJOR_VERSION", "MINOR_VERSION"};
        return fieldNames;
    }
}