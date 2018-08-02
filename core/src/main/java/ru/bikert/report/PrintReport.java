package ru.bikert.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import org.json.simple.JSONArray;
import ru.bikert.servlets.IndexServlet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PrintReport  {

    public static void printReport(JSONArray json) {
        String reportSrcFile = "C:\\Users\\ebikert\\JaspersoftWorkspace\\MyReports\\MyFirstReport.jrxml";
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

            // PDF Exportor.
            JRPdfExporter exporter = new JRPdfExporter();

            ExporterInput exporterInput = new SimpleExporterInput(print);
            // ExporterInput
            exporter.setExporterInput(exporterInput);

            // ExporterOutput
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("D:\\download\\jasperpdfexample.pdf");

            exporter.setExporterOutput(exporterOutput);

            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }catch (JRException e){
            e.getMessage();
        }


    }
}
