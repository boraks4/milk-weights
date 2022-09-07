package application;

import java.util.ArrayList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * A class to generate BarCharts representing MilkStats, for use in the GUI.
 * @author Sean
 */
public class MilkGraph {
  
  /**
   * Constructs a BarChart visualizing the MilkStats of one farm, per month, for a given year.
   * @param farm the farm to draw data from
   * @param year the year to draw data from
   * @return a BarChart visualizing MilkStats over the year
   */
  public static BarChart<String, Number> constructFarmReport(Farm farm, int year) {
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Weight");
    
    BarChart<String, Number> farmReport = new BarChart<>(xAxis, yAxis);
    FarmYear reportYear = farm.findYear(year);
    XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
    dataSeries.setName(farm.getFarmId());
    for (int month = 0; month < 12; month++) {
      dataSeries.getData().add(new XYChart.Data<String, Number>(FarmYear.getMonthName(month), 
          reportYear.sumOfWeightInMonth(month)));
    }
    farmReport.getData().add(dataSeries);
    
    return farmReport;
  }
  
  /**
   * Constructs a BarChart visualizing the MilkStats of one farm for a given year and month.
   * Only has one bar, by definition; mostly implemented for completeness.
   * @param farm the farm to draw data from
   * @param year the year to draw data from
   * @param month the month to draw data from
   * @return a BarChart visualizing MilkStats of a month
   */
  public static BarChart<String, Number> constructMonthlyFarmReport(Farm farm, int year, int month) {
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Weight");
    
    BarChart<String, Number> farmReport = new BarChart<>(xAxis, yAxis);
    FarmYear reportYear = farm.findYear(year);
    XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
    dataSeries.setName(farm.getFarmId());
    dataSeries.getData().add(new XYChart.Data<String, Number>(FarmYear.getMonthName(month), 
        reportYear.sumOfWeightInMonth(month)));
    farmReport.getData().add(dataSeries);
    
    return farmReport;
  }
  
  /**
   * Constructs a BarChart visualizing the MilkStats of multiple farms, per month, for a given year.
   * @param fc the FarmCollection representing the farms to draw data from
   * @param year the year to draw data from
   * @return a BarChart visualizing MilkStats of a year
   */
  public static BarChart<String, Number> constructFarmCollectionReport(FarmCollection fc, int year) {
    ArrayList<Farm> farmList = fc.getFarmCollection();
    
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Weight");
    
    BarChart<String, Number> farmCollectionReport = new BarChart<>(xAxis, yAxis);
    for (Farm f : farmList) {
      FarmYear reportYear = f.findYear(year);
      XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
      dataSeries.setName(f.getFarmId());
      for (int month = 0; month < 12; month++) {
        dataSeries.getData().add(new XYChart.Data<String, Number>(FarmYear.getMonthName(month), 
            reportYear.sumOfWeightInMonth(month)));
      }
      farmCollectionReport.getData().add(dataSeries);
    }
    return farmCollectionReport;
  }
  
  /**
   * Constructs a BarChart visualizing the MilkStats of multiple farms, per farm, for a given month.
   * @param fc the FarmCollection representing the farms to draw data from
   * @param year the year to draw data from
   * @param month the month to draw data from
   * @return a BarChart visualizing MilkStats of a month for all farms
   */
  public static BarChart<String, Number> constructMonthlyFarmCollectionReport(FarmCollection fc,
      int year, int month) {
    ArrayList<Farm> farmList = fc.getFarmCollection();
    
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Weight");
    
    BarChart<String, Number> farmCollectionReport = new BarChart<>(xAxis, yAxis);
    XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
    dataSeries.setName(FarmYear.getMonthName(month) + " " + year);
    for (Farm f : farmList) {
      FarmYear reportYear = f.findYear(year); 
      dataSeries.getData().add(new XYChart.Data<String, Number>(f.getFarmId(), 
            reportYear.sumOfWeightInMonth(month)));
    }
    farmCollectionReport.getData().add(dataSeries);
    return farmCollectionReport;
  }
}
