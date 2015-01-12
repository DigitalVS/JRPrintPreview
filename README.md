# JRPrintPreview
JasperReports print preview window in JavaFX 8

### Usage Example
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
