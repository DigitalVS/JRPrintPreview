# JRPrintPreview
JasperReports print preview stage class written in JavaFX 8

### Usage Example
Whole functionality is contained in a `JRPrintPreview.java` file. This file should be copied to your project. The rest of the files represent an example project which demonstrates print preview functionality for one simple JasperReports report.

Basic usage is shown in the next example.

```
String reportFileName = "SomeReport.jasper";
Map<String, Object> paramMap = new HashMap<>();
JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(...);

JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
	JRLoader.getResourceInputStream(reportFileName));
JasperPrint jasperPrint = JasperFillManager.fillReport(
	jasperReport, paramMap, dataSource);

JRPrintPreview printPreview = new JRPrintPreview(jasperPrint);
printPreview.show();
```
