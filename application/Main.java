package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The main driver class for Milk Weights, processing user data and displaying it in a GUI.
 * @author Sean
 */
public class Main extends Application {  
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 600;
  private static final String APP_TITLE = "Milk Weights";
  private static final String IO_ERROR = "There was an error with the file you attempted to use.";
  private static final String CSV_ERROR = "Files must be of type .csv.";
  private static final String FORMAT_ERROR = "Your submission was not formatted correctly. Ensure "
      + "that the date is in YYYY-MM-DD format and that weight is numeric.";
  public static List<File> inputFiles = new ArrayList<>();
  public static File outputFile = new File("");
  public static FarmCollection fc = new FarmCollection();
  
  /**
   * Creates the GUI for the application.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {    
    // INPUT TAB
    Tab inputTab = new Tab("Add Data");
    inputTab.setStyle("-fx-background-color: darkorange;");
    
      BorderPane inputPane = new BorderPane();
      
      Label fileLabel = new Label("Get info from CSV file:");
        fileLabel.setStyle("-fx-font: 16 arial;");
  
      Label manualLabel = new Label("Add data by hand:");
        manualLabel.setStyle("-fx-font: 16 arial;");
      
      VBox buttonBox = new VBox(50);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().add(fileLabel);
        buttonBox.getChildren().add(manualLabel);
        
      inputPane.setLeft(buttonBox);
      
      VBox inputBoxes = new VBox(40);
      inputBoxes.setPadding(new Insets(10));
      inputBoxes.setAlignment(Pos.CENTER_LEFT);
        HBox fileAndLabel = new HBox(10);
          FileChooser fileChooser = new FileChooser();
          fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // open in working directory
          Label fileText = new Label("Select a file...");
          Button fileSelectButton = new Button("Open");
            EventHandler<MouseEvent> onFileSelectPress = new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent click) {
                inputFiles = fileChooser.showOpenMultipleDialog(primaryStage);
                if (inputFiles != null) {
                  String label = inputFiles.get(0).getAbsolutePath();
                  int fileCount = inputFiles.size();
                  if (fileCount > 1) {
                    label += String.format(" and %s other", fileCount - 1) + (fileCount > 2 ? "s" : "");
                  }
                  fileText.setText(label);

                  for (File inputFile : inputFiles) {
                    if (isCSV(inputFile)) {
                      try {
                        fc.addFile(inputFile);
                      } catch (IOException e) {
                        Alert ioAlert = new Alert(AlertType.ERROR);
                        ioAlert.setTitle("Error");
                        ioAlert.setHeaderText("File Error");
                        ioAlert.setContentText(IO_ERROR);
                        ioAlert.show();
                      }
                    }
                    else {
                      Alert csvAlert = new Alert(AlertType.ERROR);
                      csvAlert.setTitle("Error");
                      csvAlert.setHeaderText("Format Error");
                      csvAlert.setContentText(CSV_ERROR);
                      csvAlert.show();
                    }
                  }
                }
              }
            };
            fileSelectButton.setOnMouseReleased(onFileSelectPress);
            fileAndLabel.getChildren().add(fileSelectButton);
            fileAndLabel.getChildren().add(fileText);
        inputBoxes.getChildren().add(fileAndLabel);
        
        TableView<MilkStat> dataTable = new TableView<>();
          TableColumn<MilkStat, String> dateColumn = new TableColumn<>("Date");
          dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
          TableColumn<MilkStat, String> idColumn = new TableColumn<>("Farm ID");
          idColumn.setCellValueFactory(new PropertyValueFactory<>("farmId"));
          TableColumn<MilkStat, String> weightColumn = new TableColumn<>("Weight");
          weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
          dataTable.getColumns().add(dateColumn);
          dataTable.getColumns().add(idColumn);
          dataTable.getColumns().add(weightColumn);
          dataTable.setMaxSize(300, 400);
        inputBoxes.getChildren().add(dataTable);
        
        HBox tableInputBoxes = new HBox(10);
          TextField dateField = new TextField("Date... (YYYY-MM-DD)");
          TextField idField = new TextField("Farm ID...");
          TextField weightField = new TextField("Weight...");
          Button manualSubmit = new Button("Submit");
            
          tableInputBoxes.getChildren().add(dateField);
          tableInputBoxes.getChildren().add(idField);
          tableInputBoxes.getChildren().add(weightField);
          tableInputBoxes.getChildren().add(manualSubmit);
        inputBoxes.getChildren().add(tableInputBoxes);
        
        HBox fileOutputSelection = new HBox(10);
          CheckBox outputCb = new CheckBox("Output to CSV?");
          fileOutputSelection.getChildren().add(outputCb);
          Label outputFileText = new Label("Select a file...");
          FileChooser fileOutputChooser = new FileChooser();
          fileOutputChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // open in working directory
          Button fileOutputButton = new Button("Open");
            EventHandler<MouseEvent> onFileOutputPress = new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent click) {
                outputFile = fileOutputChooser.showOpenDialog(primaryStage);
                if (outputFile != null) {
                  outputFileText.setText(outputFile.getAbsolutePath());
                  if (!isCSV(outputFile)) {
                    Alert csvAlert = new Alert(AlertType.ERROR);
                    csvAlert.setTitle("Error");
                    csvAlert.setHeaderText("Format Error");
                    csvAlert.setContentText(CSV_ERROR);
                    csvAlert.show();
                  }
                }
              }
            };
            fileOutputButton.setOnMouseReleased(onFileOutputPress);
            fileOutputSelection.getChildren().add(fileOutputButton);
            fileOutputSelection.getChildren().add(outputFileText);
        inputBoxes.getChildren().add(fileOutputSelection);
        
        EventHandler<MouseEvent> onManualSubmitPress = new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent click) {
            String dateText = dateField.getText();
            String id = idField.getText();
            String weightText = weightField.getText();
            
            try {
              Calendar date = textToDate(dateText);
              int weight = Integer.valueOf(weightText);
              MilkStat newStat = new MilkStat(date, id, weight);
              fc.addMilkStat(newStat);

              Calendar displayDate = (Calendar) date.clone();
              displayDate.set(Calendar.MONTH, date.get(Calendar.MONTH) + 1);
              MilkStat displayStat = new MilkStat(displayDate, id, weight);
              dataTable.getItems().add(displayStat);

              if (outputCb.isSelected() && isCSV(outputFile)) {
                FileWriter append = new FileWriter(outputFile, true);
                String appendStr = dateText + "," + id + "," + weightText;
                append.write(appendStr);
                append.close();
              }
            }
            catch (NumberFormatException e) {
              Alert formatAlert = new Alert(AlertType.ERROR);
              formatAlert.setTitle("Error");
              formatAlert.setHeaderText("Format Error");
              formatAlert.setContentText(FORMAT_ERROR);
              formatAlert.show();
            } catch (IOException e) {
              System.out.println(e.getMessage());
              Alert ioAlert = new Alert(AlertType.ERROR);
              ioAlert.setTitle("Error");
              ioAlert.setHeaderText("File Error");
              ioAlert.setContentText(IO_ERROR);
              ioAlert.show();
            }
          }
        };
        manualSubmit.setOnMouseClicked(onManualSubmitPress);
        
    inputPane.setCenter(inputBoxes);
      
    inputTab.setContent(inputPane);
    
    // OUTPUT TAB
    Tab outputTab = new Tab("See Data");
    outputTab.setStyle("-fx-background-color: darkorange;");

    
      BorderPane outputPane = new BorderPane();
      
      HBox outputOptions = new HBox(200);
        outputOptions.setPadding(new Insets(10));
        VBox farmOptions = new VBox(10);
          Label farmLabel = new Label("Farm:");
          ToggleGroup farmButtons = new ToggleGroup();
          RadioButton allRadioButton = new RadioButton("All farms");
            allRadioButton.setToggleGroup(farmButtons);
          HBox oneRadioButtonBox = new HBox(10);
            RadioButton oneRadioButton = new RadioButton("One:");
              oneRadioButton.setToggleGroup(farmButtons);
            TextField oneFarmField = new TextField("Farm ID...");
            oneRadioButtonBox.getChildren().addAll(oneRadioButton, oneFarmField);
          farmOptions.getChildren().addAll(farmLabel, allRadioButton, oneRadioButtonBox);
          
        VBox timeOptions = new VBox(10);
          Label timeLabel = new Label("Timeframe:");
          ToggleGroup timeButtons = new ToggleGroup();
          HBox yearButtonBox = new HBox(10);
            RadioButton yearRadioButton = new RadioButton("Year:");
              yearRadioButton.setToggleGroup(timeButtons);
            TextField yearField = new TextField();
            yearButtonBox.getChildren().addAll(yearRadioButton, yearField);
          HBox monthButtonBox = new HBox(10);
            RadioButton monthRadioButton = new RadioButton("Month");
              monthRadioButton.setToggleGroup(timeButtons);
            TextField monthField = new TextField();
            Button generateButton = new Button("Generate");
            EventHandler<MouseEvent> onGeneratePress = new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent click) {
                RadioButton farmSelect = (RadioButton) farmButtons.getSelectedToggle();
                RadioButton timeSelect = (RadioButton) timeButtons.getSelectedToggle();
                
                try {
                  HBox graph = new HBox();
                  graph.getChildren().removeAll();
                  BarChart<String, Number> bc = new BarChart<String, Number>(new CategoryAxis(), 
                      new NumberAxis());
                  if (farmSelect == allRadioButton) {
                    if (timeSelect == yearRadioButton) {
                      String year = yearField.getText();
                      if (isNumeric(year)) {
                        bc = MilkGraph.constructFarmCollectionReport(fc, 
                            Integer.valueOf(year));
                      }
                    }
                    else if (timeSelect == monthRadioButton) {
                      String month = monthField.getText();
                      String year = yearField.getText();
                      if (isNumeric(year) && isNumeric (month)) {
                        int monthNum = Integer.valueOf(month) - 1; // January = 1
                        if (0 <= monthNum && monthNum < 12) {
                          bc = MilkGraph.constructMonthlyFarmCollectionReport(fc, 
                              Integer.valueOf(year), monthNum);
                        }
                      }
                    }
                  }
                  else if (farmSelect == oneRadioButton) {
                    Farm reportFarm = fc.getFarm(oneFarmField.getText());
                    String year = yearField.getText();

                    if (timeSelect == yearRadioButton) {
                      if (isNumeric(year)) {
                        bc = MilkGraph.constructFarmReport(reportFarm, Integer.valueOf(year));
                      }
                    }

                    else if (timeSelect == monthRadioButton) {
                      String month = monthField.getText();
                      if (isNumeric(year) && isNumeric(month)) {
                        int monthNum = Integer.valueOf(monthField.getText()) - 1;
                        if (0 <= monthNum && monthNum < 12) {
                          bc = MilkGraph.constructMonthlyFarmReport(reportFarm,
                              Integer.valueOf(yearField.getText()), monthNum);
                        }
                      }
                    }
                  }
                  bc.setMinWidth(WINDOW_WIDTH);
                  graph.getChildren().add(bc);
                  outputPane.setCenter(graph);
                }
                catch (NullPointerException e) {
                  
                }
              }
            };
            generateButton.setOnMouseClicked(onGeneratePress);
            monthButtonBox.getChildren().addAll(monthRadioButton, monthField, generateButton);
          timeOptions.getChildren().addAll(timeLabel, yearButtonBox, monthButtonBox);
          
          outputOptions.getChildren().addAll(farmOptions, timeOptions);
            
        outputPane.setTop(outputOptions);
        
        
        
    outputTab.setContent(outputPane);
    
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    tabPane.getTabs().add(inputTab);
    tabPane.getTabs().add(outputTab);
    tabPane.setTabMinWidth(WINDOW_WIDTH / tabPane.getTabs().size() - 20);
    tabPane.setTabMaxWidth(WINDOW_WIDTH / tabPane.getTabs().size() - 20);
    
    VBox tabBox = new VBox(tabPane);
    
    BorderPane root = new BorderPane();
    root.setCenter(tabBox);
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.DARKORANGE);
    
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }
  
  /**
   * Begins the application.
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
  
  /**
   * Checks if a given String can be safely converted using Integer.valueOf().
   * @param str the String to check
   * @return true if the String represents a number; false otherwise
   */
  public static boolean isNumeric(String str)
  {
    try
    {
      double d = Double.parseDouble(str);
    }
    catch (NumberFormatException e)
    {
      return false;
    }
    
    return true;
  }
  
  /**
   * Checks if the given File is a .csv.
   * @param file the file to check
   * @return true if the given File is a .csv; false otherwise
   */
  public static boolean isCSV(File file) {
    String fileName = file.getName();
    return fileName.substring(fileName.lastIndexOf(".")).equals(".csv"); // if extension is .csv
  }
  
  /**
   * Converts a String of the format YYYY/MM/DD into a Calendar object. For use with manual data
   * entry.
   * @param dateText the text to convert to a Calendar
   * @return a Calendar with the properties of dateText
   * @throws NumberFormatException if dateText is not of the form YYYY/MM/DD or represents an
   *    impossible date.
   */
  public static Calendar textToDate(String dateText) {
    final int[] NUMBER_OF_DAYS_FOR_MONTHS = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    String[] dateArray = dateText.split("-");
    if (dateArray.length != 3) {
      throw new NumberFormatException("Dates must be formatted as YYYY/MM/DD");
    }
    
    try {
      String yearText = dateArray[0];
      int year = Integer.valueOf(yearText);
      
      String monthText = dateArray[1];
      int month = Integer.valueOf(monthText) - 1;
      if (!(0 <= month && month < 12)) {
        throw new NumberFormatException("Months must be between 0 and 11");
      }
      
      String dayText = dateArray[2];
      int day = Integer.valueOf(dayText);
      int daysInMonth = NUMBER_OF_DAYS_FOR_MONTHS[month];
      if (!(0 <= day && day < daysInMonth)) {
        throw new NumberFormatException("Day is not in valid range for month");
      }
      
      Calendar date = new GregorianCalendar();
      date.set(Calendar.YEAR, year);
      date.set(Calendar.MONTH, month);
      date.set(Calendar.DAY_OF_MONTH, day);
      return date;
    }
    catch (NumberFormatException e) {
      throw new NumberFormatException("Dates must be numeric");
    }
  }
}
