package jrprintpreview;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Cursor;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import java.io.File;
import java.util.Map;

public class ReportPrint {
  private JasperReport jasperReport;
  private Window ownerWindow;

  public ReportPrint(String reportFileName, Window ownerWindow) {
    try {
      jasperReport = (JasperReport) JRLoader.loadObject(JRLoader.getResourceInputStream(reportFileName));
      this.ownerWindow = ownerWindow;
    } catch (JRException ex) {
      ex.printStackTrace();
    }
  }

  public void export(String fileChooserTitle, String fileName, Map<String, Object> paramMap, JRBeanCollectionDataSource dataSource) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(fileChooserTitle);
    fileChooser.setInitialFileName(fileName);
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
        new FileChooser.ExtensionFilter("Excel files", "*.xlsx"),
        new FileChooser.ExtensionFilter("Word files", "*.docx"));
    File selectedFile = fileChooser.showSaveDialog(ownerWindow);

    if (selectedFile != null) {
      ownerWindow.getScene().setCursor(Cursor.WAIT);

      String extension = "";
      int i = selectedFile.getName().lastIndexOf('.');
      if (i > 0)
        extension = fileName.substring(i + 1);

      try {
        Exporter exporter;

        switch (extension.toLowerCase()) {
          case "pdf":
            exporter = new JRPdfExporter();
            SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
            config.setMetadataCreator("App name");
            exporter.setConfiguration(config);
            break;
          case "xlsx":
            exporter = new JRXlsxExporter();
            break;
          case "docx":
            exporter = new JRDocxExporter();
            break;
          default:
            return;
        }

        exporter.setExporterInput(new SimpleExporterInput(JasperFillManager.fillReport(jasperReport, paramMap, dataSource)));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(selectedFile));
        exporter.exportReport();
      } catch (JRException ex) {
        ex.printStackTrace();
      } catch (JRRuntimeException ex) { // File is being used by another process and cannot be overwritten
        System.out.println(ex.getMessage());
      } finally {
        ownerWindow.getScene().setCursor(Cursor.DEFAULT);
      }
    }
  }

  public void print(Map<String, Object> paramMap, JRBeanCollectionDataSource dataSource) {
    createReportTask(paramMap, dataSource, true);
  }

  public void printPreview(Map<String, Object> paramMap, JRBeanCollectionDataSource dataSource) {
    createReportTask(paramMap, dataSource, false);
  }

  private void createReportTask(Map<String, Object> paramMap, JRBeanCollectionDataSource dataSource, boolean printOrPreview) {
    Task<JasperPrint> task = new Task<JasperPrint>() {
      @Override
      protected JasperPrint call() throws Exception {
        ownerWindow.getScene().setCursor(Cursor.WAIT);
        return JasperFillManager.fillReport(jasperReport, paramMap, dataSource);
      }
    };

    task.setOnSucceeded((WorkerStateEvent event) -> {
      ownerWindow.getScene().setCursor(Cursor.DEFAULT);

      if (printOrPreview)
        reportPrint(task);
      else
        reportPreview(task);
    });

    task.setOnFailed(event -> ownerWindow.getScene().setCursor(Cursor.DEFAULT));

    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }

  private void reportPreview(Task<JasperPrint> task) {
    JRPrintPreview printPreview = new JRPrintPreview(task.getValue());
    printPreview.initOwner(ownerWindow);
    printPreview.show();
  }

  private void reportPrint(Task<JasperPrint> task) {
    try {
      JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance()).print(task.getValue(), true);
    } catch (JRException ex) {
      ex.printStackTrace();
    }
  }
}
