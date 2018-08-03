package ru.bikert.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.*;
import org.json.simple.JSONArray;
import ru.bikert.servlets.IndexServlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PrintReport  {

    public static void printReport(JSONArray json, String format) {
        String reportSrcFile = "C:\\Users\\ebikert\\IdeaProjects\\FileNetWebApp\\core\\src\\main\\resources\\MyFirstReport.jrxml";
        try {
            // преобразует xml формат отчета в некоторый внутренний формат
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

            // Parameters for report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Employee", IndexServlet.getCurrentEmployee().get_Name());

            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JsonToDataSource(json));

            // Make sure the output directory exists.
            File outDir = new File("D:\\download");
            outDir.mkdirs();


//
//            if (format.equals("pdf")){
//                JRPdfExporter exporter = new JRPdfExporter();
//                ExporterInput exporterInput = new SimpleExporterInput(print);
//                exporter.setExporterInput(exporterInput);
//                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("D:\\download\\jasperpdfexample.pdf ");
//                exporter.setExporterOutput(exporterOutput);
//                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//            } else if (format.equals("docx")){
//                JRDocxExporter exporter = new JRDocxExporter();
//                ExporterInput exporterInput = new SimpleExporterInput(print);
//                exporter.setExporterInput(exporterInput);
//                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("D:\\download\\jasperpdfexample.docx");
//                exporter.setExporterOutput(exporterOutput);
//                SimpleDocxExporterConfiguration configuration = new SimpleDocxExporterConfiguration();
//            } else {

            JRXlsExporter exporter = new JRXlsExporter();
            ExporterInput exporterInput = new SimpleExporterInput(print);
            exporter.setExporterInput(exporterInput);

//            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("D:\\download\\jasperpdfexample.xlc ");
//          exporter.setExporterOutput(exporterOutput);
            SimpleXlsExporterConfiguration configuration = new SimpleXlsExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();


        }catch (JRException e){
            e.getMessage();
        }
    }
}
