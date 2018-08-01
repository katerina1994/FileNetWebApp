package ru.bikert.servlets.Operations;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;

public class RequestHelper {

    public static String getBody(HttpServletRequest request) throws IOException {

        if ((request.getContentType()).contains("form")) {
            JSONObject result = new JSONObject();
            Collection<Part> parts = null;
            try {
                String appPath = System.getProperty("user.dir");
                String savePath = appPath + File.separator + "uploads";

                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                parts = request.getParts();
                for (Part part : parts) {
                    String fileName = part.getName();
                    if (fileName.equals("filePath")) {
                        String fileDocName = extractFileName(part);
                        try {
                            part.write(fileDocName);
                            result.put("filePath", fileDocName);
                        } catch (Exception e) {
                            result.put("message", e);
                            result.put("type", "error");
                        }


                    } else {
                        Enumeration en = request.getParameterNames();
                        while (en.hasMoreElements()) {
                            Object objOri = en.nextElement();
                            String param = (String) objOri;
                            String value = request.getParameter(param);
                            result.put(param, value);
                        }
                    }
                }

            } catch (ServletException e) {
                result.put("message", e);
                result.put("type", "error");
            }
            return result.toJSONString();
        } else {

            String body = null;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;

            try {
                String line = null;
                bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
//            bufferedReader = request.getReader();
                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line);
            } catch (IOException ex) {
                throw ex;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        throw ex;
                    }
                }
            }

            body = stringBuilder.toString();
            return body;
        }
    }

    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}

