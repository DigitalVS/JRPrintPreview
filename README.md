# JRPrintPreview
JasperReports print preview stage class written in JavaFX 8

### Usage Example
Whole functionality is contained in a `JRPrintPreview.java` file. This file should be copied to your project. The rest of the files represent a Maven example project which demonstrates print preview functionality for one simple JasperReports report.

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
### Running on Java 9 and later versions

The example project should work on Java 9 and 10 as is if used with legacy classpath. Repository contains some additional files which have to be used to compile and execute provided example
as Java 9+ module path application.

For module path application, provided files `pom.xml.12` and `module-info.java.12` have to be renamed to `pom.xml` and `module-info.java` respectively. Pom.xml file is written specifically for Java 12 (as file extension indicates).
For other Java versions user needs to edit that `pom.xml` file and to change Java version for Maven compiler plugin `source` and `target` tags, as well as all `version` tags for JavaFX dependencies.