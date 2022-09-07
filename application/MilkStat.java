package application;

import java.util.Calendar;

/**
 * A class representing a weight of milk produced by a specified farm, on a specified day. 
 * The lowest level of the Milk Weights data hierarchy.
 * @author Sean
 */
public class MilkStat {
  private Calendar date; // the date this weight was recorded on
  private String dateString; // used for cell factory in GUI
  private String farmId; // the name of the farm this weight belongs to
  private int weight; // the weight itself
  
  /**
   * Construct a new instance of MilkStat.
   * @param date the date this stat was recorded on
   * @param farmId the name of the farm this stat belongs to
   * @param weight the weight of milk recorded
   */
  public MilkStat(Calendar date, String farmId, int weight) {
    this.date = date;
    this.farmId = farmId;
    this.weight = weight;
  }
  
  /**
   * Returns a String representation of this MilkStat.
   * @return a String representation of this MilkStat
   */
  @Override
  public String toString() {
    String milkStr = "";
    milkStr += date.get(Calendar.YEAR) + "/";
    milkStr += date.get(Calendar.MONTH) + "/";
    milkStr += date.get(Calendar.DAY_OF_MONTH) + ", ";
    milkStr += farmId + ", ";
    milkStr += weight;
    return milkStr;
  }
  
  /**
   * Returns the date this MilkStat represents.
   * @return the date
   */
  public Calendar getDate() {
    return date;
  }
  
  /**
   * Returns a String representing the date of this MilkStat.
   * @return a String representing date
   */
  public String getDateString() {
    return getYear() + "-" + getMonth() + "-" + getDay();
  }
  
  /**
   * Returns the year of the date of this MilkStat
   * @return the year of the date
   */
  public int getYear() {
    return date.get(Calendar.YEAR);
  }
  
  /**
   * Returns the month of the date of this MilkStat
   * @return the month of the date
   */
  public int getMonth() {
    return date.get(Calendar.MONTH);
  }
  
  /**
   * Returns the day of this date of this MilkStat
   * @return the day of the date
   */
  public int getDay() {
    return date.get(Calendar.DAY_OF_MONTH);
  }
  
  /**
   * Sets the date of this MilkStat
   * @param date the new date
   */
  public void setDate(Calendar date) {
    this.date = date;
  }
  
  /**
   * Gets the name of the Farm this MilkStat belongs to.
   * @return the name of the farm
   */
  public String getFarmId() {
    return farmId;
  }
  
  /**
   * Sets the name of the Farm this MilkStat belongs to.
   * @param farmId the new name
   */
  public void setFarmId(String farmId) {
    this.farmId = farmId;
  }

  /**
   * Returns the weight of milk produced on the date of this MilkStat
   * @return the weight of this MilkStat
   */
  public int getWeight() {
    return weight;
  }

  /**
   * Sets the weight of milk produced on the date of this MilkStat
   * @param weight the new weight
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }
}
