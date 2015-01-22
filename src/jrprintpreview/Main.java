package jrprintpreview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {
  Stage stage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;

    VBox root = new VBox();
    root.getChildren().add(createMenus());

    primaryStage.setTitle("JRPrintPreview Example App");
    primaryStage.setScene(new Scene(root, 300, 275));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  private MenuBar createMenus() {
    Menu menuFile = new Menu("_File");
    MenuItem exportMenu = new MenuItem("_Export...");
    exportMenu.setOnAction(e -> export());
    MenuItem printMenu = new MenuItem("_Print...");
    printMenu.setOnAction(e -> print());
    MenuItem printPreviewMenu = new MenuItem("Print pre_view...");
    printPreviewMenu.setOnAction(e -> printPreview());
    MenuItem closeMenu = new MenuItem("_Close");
    closeMenu.setOnAction(e -> stage.close());
    menuFile.getItems().addAll(exportMenu, printMenu, printPreviewMenu, new SeparatorMenuItem(), closeMenu);

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(menuFile);
    return menuBar;
  }

  private void export() {
    Report report = new Report("reports/Example.jasper", stage.getScene().getWindow());
    report.export("Export report", "Report " + ".pdf", getReportParams(), new JRBeanCollectionDataSource(getReportData()));
  }

  private void print() {
    Report report = new Report("reports/Example.jasper", stage.getScene().getWindow());
    report.print(getReportParams(), new JRBeanCollectionDataSource(getReportData()));
  }

  private void printPreview() {
    Report report = new Report("reports/Example.jasper", stage.getScene().getWindow());
    report.printPreview(getReportParams(), new JRBeanCollectionDataSource(getReportData()));
  }

  private Map<String, Object> getReportParams() {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("COMPANY_NAME", "Company inc.");
    return paramMap;
  }

  private List<Person> getReportData() {
    List<Person> items = new ArrayList<>();
    items.add(new Person("Alan", "Smithee"));
    items.add(new Person("John", "Doe"));
    items.add(new Person("Average", "Joe"));
    items.add(new Person("Hong", "Gildong"));
    items.add(new Person("Joe", "Bloggs"));
    items.add(new Person("Joe", "Shmoe"));
    items.add(new Person("John Q.", "Public"));
    items.add(new Person("Luther", "Blissett"));
    items.add(new Person("Rudolf", "Lingens"));
    items.add(new Person("Tommy", "Atkins"));
    items.add(new Person("Richard", "Roe"));
    items.add(new Person("John", "Smith"));
    return items;
  }
}
