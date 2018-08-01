package ru.bikert.servlets;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.bikert.servlets.Operations.*;
import ru.bikert.servlets.Operations.buttonOperation.*;
import ru.bikert.servlets.Operations.printOperations.GetCurrentChildrenOperation;
import ru.bikert.servlets.Operations.printOperations.GetEmployee;
import ru.bikert.servlets.Operations.printOperations.GetHierarchyOperation;
import ru.bikert.servlets.Operations.printOperations.GetReportThroughSQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Collection;


import static ru.bikert.servlets.Operations.RequestHelper.*;

@WebServlet
@MultipartConfig
public class OperationServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(OperationServlet.class);

    public OperationServlet() {
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


        JSONObject result = trafficOperation(req);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.append(result.toJSONString());
    }

    protected static JSONObject trafficOperation(HttpServletRequest req) {

        JSONParser parser = new JSONParser();
        try {
            String body = getBody(req);
            JSONObject json = (JSONObject) parser.parse(body);
            String nameOperation = (String) json.get("nameOperation");
            switch (nameOperation) {
                case "getHierarchy":
                    Operation getHierarchy = new GetHierarchyOperation();
                    return getHierarchy.performTask(json);
                case "printCurrentFolder":
                    Operation printCurrentFolder = new GetCurrentChildrenOperation();
                    return printCurrentFolder.performTask(json);
                case "printButtonList":
                    return getJsonOperation();
                case "create":
                    Operation create = new CreateOperation();
                    return create.performTask(json);
                case "delete":
                    Operation delete = new DeleteOperation();
                    return delete.performTask(json);
                case NamingOperation.TRANSFER:
                    Operation transfer = new TrunsferDocumentOperation();
                    return transfer.performTask(json);
                case NamingOperation.REPORT:
                    Operation report = new GetReportThroughSQL();
                    return report.performTask(json);
                case NamingOperation.EDIT:
                    Operation edit = new EditOperation();
                    return edit.performTask(json);
                case "printCurrentEmployee":
                    Operation getEmployee = new GetEmployee();
                    return getEmployee.performTask(json);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject getJsonOperation(){
        JSONObject json = new JSONObject();
        JSONArray buttons = new JSONArray();
        for (Operation o : IndexServlet.getOperations().values()) {
            JSONObject button = new JSONObject();
            button.put("buttonTitle", o.getButtonTitle());
            button.put("nameOperation", o.getNameOperation());
            buttons.add(button);
        }
        json.put("buttons", buttons);
        return json;
    }
}
