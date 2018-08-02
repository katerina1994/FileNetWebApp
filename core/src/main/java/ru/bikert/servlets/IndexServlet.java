package ru.bikert.servlets;

import com.filenet.api.core.CustomObject;
import com.filenet.api.core.IndependentObject;
import ru.bikert.servlets.Operations.buttonOperation.*;
import ru.bikert.servlets.Operations.Operation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IndexServlet extends HttpServlet {

    private static Map<String, Operation> operations = new HashMap<>();
    private static CustomObject currentEmployee;

    public static void setCurrentEmployee(CustomObject currentEmployee) { IndexServlet.currentEmployee = currentEmployee; }

    public static CustomObject getCurrentEmployee() { return currentEmployee; }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setOperations();
        getServletContext().getRequestDispatcher("/hierarchy.jsp").forward(req, resp);
    }

    public static Map<String, Operation> getOperations() {
        return operations;
    }
    private static void setOperations() {
        operations.put(NamingOperation.NameButtonOperation.СREATE, new CreateOperation());
        operations.put(NamingOperation.NameButtonOperation.DELETE, new DeleteOperation());
        operations.put(NamingOperation.NameButtonOperation.TRANSFER, new TrunsferDocumentOperation());
        operations.put(NamingOperation.NameButtonOperation.REPORT, new ReportOperation());
        operations.put(NamingOperation.NameButtonOperation.EDIT, new EditOperation());


    }
}
