package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FarmYear {
  private static final String[] MONTH_LIST = {"January", "February", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December"};
  private int year; // the year this FarmYear represents
  private List<ArrayList<MilkStat>> farmYear; // a collection of MilkStat lists representing 12 months
  
  /**
   * Constructs a new FarmYear representing the given year.
   * @param year the year this FarmYear will represent
   */
  public FarmYear(int year) {
    this.year = year;
    farmYear = new ArrayList<ArrayList<MilkStat>>();
    
    for (int month = 0; month < MONTH_LIST.length; month++) {
      farmYear.add(new ArrayList<MilkStat>());
    }
  }
  
  /**
   * Add a MilkStat to this FarmYear, sorted based on the month it occured in. Adding two MilkStats
   * representing the same date is discouraged, as it may result in unexpected behavior.
   * @param ms the MilkStat to be added
   */
  public void addMilkStat(MilkStat ms) {
    Calendar date = ms.getDate();
    int monthNum = date.get(Calendar.MONTH);
    List<MilkStat> monthList = farmYear.get(monthNum);
    monthList.add(ms);
  }
  
  /**
   * Returns the sum of the weight of every MilkStat in this FarmYear.
   * @return the sum of every MilkStat
   */
  public int sumOfWeightInYear() {
    int weightSum = 0;
    for (int month = 0; month < MONTH_LIST.length; month++) {
      weightSum += sumOfWeightInMonth(month);
    }
    return weightSum;
  }
  
  /**
   * Returns the sum of the weight of every MilkStat in a given month.
   * @param month the month to sum the MilkStats for
   * @return the sum of every MilkStat in a month
   */
  public int sumOfWeightInMonth(int month) {
    ArrayList<MilkStat> monthList = farmYear.get(month);
    int weightSum = 0;
    
    for (MilkStat ms : monthList) {
      weightSum += ms.getWeight();
    }
    return weightSum;
  }
  
  /**
   * Returns a String representation of this FarmYear.
   * @return a String representation of this FarmYear
   */
  @Override
  public String toString() {
    String str = "";
    for (int month = 0; month < MONTH_LIST.length; month++) {
      str += MONTH_LIST[month] + ": " + farmYear.get(month).toString() + "\n";
    }
    return str;
  }
  
  /**
   * Returns the year that this FarmYear represents.
   * @return the year of this FarmYear
   */
  public int getYear() {
    return year;
  }
  
  /**
   * Sets the year that this FarmYear represents.
   * @param year the new year to set to
   */
  public void setYear(int year) {
    this.year = year;
  }
  
  /**
   * A method to retrieve the name of English months from 0-11.
   * @param month the month number to retrieve the name of
   * @return the name of the specified month
   */
  public static String getMonthName(int month) {
    try {
      return MONTH_LIST[month];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Argument must be between 0 and 11");
    }
  }
  
  /**
   * Get the List of MilkStat Lists this FarmYear represents.
   * @return the list of MilkStat Lists
   */
  public List<ArrayList<MilkStat>> getFarmYear() {
    return farmYear;
  }

}
