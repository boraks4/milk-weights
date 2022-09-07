package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects multiple instances of FarmYear, all belonging to the same farm. In other words,
 * the entire milk history of any given farm.
 * @author Sean
 */
public class Farm {
  private String farmId; // the name of the farm
  private ArrayList<FarmYear> yearList; // the list of years under this farm
  
  /**
   * Construct a new instance of Farm.
   * @param farmId the name of this farm
   */
  public Farm(String farmId) {
    this.farmId = farmId;
    yearList = new ArrayList<FarmYear>();
  }
  
  /**
   * Add a MilkStat to the list of years under this farm.
   * @param ms the MilkStat to be added
   */
  public void addMilkStat(MilkStat ms) {
    int year = ms.getYear();
    FarmYear fy = findYear(year); // add to the year if it already exists
    
    if (fy == null) { // otherwise create it
      fy = new FarmYear(year);
      addYear(fy);
    }
    fy.addMilkStat(ms);
  }
  
  /**
   * Add an empty new FarmYear representing the specified year. Two FarmYears cannot represent
   * the same year.
   * @param year the year this FarmYear represents
   * @throws IllegalArgumentException if the year is already represented by another FarmYear
   */
  public void addYear(int year) {
    addYear(new FarmYear(year));
  }
  
  /**
   * Add an already populated FarmYear. Two FarmYears cannot represent
   * the same year.
   * @param fy the FarmYear to be added
   * @throws IllegalArgumentException if the year this FarmYear represents is already represented 
   *    by another FarmYear
   */
  public void addYear(FarmYear fy) {
    int year = fy.getYear();
    if (findYear(year) != null) {
      throw new IllegalArgumentException("Two FarmYears cannot represent the same year.");
    }
    yearList.add(fy);
  }
  
  /**
   * Return the FarmYear that represents a given year.
   * @param year the year to find the FarmYear for
   * @return the FarmYear that represents year
   */
  public FarmYear findYear(int year) {
    for (FarmYear yr : yearList) {
      if (yr.getYear() == year) {
        return yr;
      }
    }
    return null;
  }
  
  /**
   * Returns the sum of all weights of milk in every FarmYear.
   * @return the sum of all weights of milk
   */
  public int sumOfAllWeights() {
    int weightSum = 0;
    for (FarmYear fy : yearList) {
      weightSum += fy.sumOfWeightInYear();
    }
    return weightSum;
  }
  
  /**
   * Returns the name of this Farm.
   * @return the name of the farm
   */
  public String getFarmId() {
    return farmId;
  }
  
  /**
   * Sets the name of this Farm.
   * @param farmId the new name for the farm
   */
  public void setFarmId(String farmId) {
    this.farmId = farmId;
  }
  
  /**
   * Returns the list of FarmYears that this Farm represents.
   * @return the list of FarmYears
   */
  public ArrayList<FarmYear> getYearList() {
    return yearList;
  }
  
  /**
   * Returns a String representation of this Farm.
   * @return a String representing this Farm
   */
  @Override
  public String toString() {
    String str = "";
    for (FarmYear fy : yearList) {
      str += fy.getYear() + ":\n";
      str += fy.toString();
      str += "\n";
    }
    return str;
  }
}
