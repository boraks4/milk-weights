package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects multiple instances of the Farm object. The top level of data hierarchy for Milk Weights.
 * @author Sean
 */
public class FarmCollection {
  private ArrayList<Farm> farmCollection; // the collection of farms stored in this FarmCollection
  
  /**
   * Constructs a new empty FarmCollection.
   */
  public FarmCollection() {
    farmCollection = new ArrayList<Farm>();
  }
  
  /**
   * Constructs a new FarmCollection, using the contents of a CSV file as initial data.
   * @param file the CSV to be parsed for construction
   * @throws IOException if the file cannot be parsed
   */
  public FarmCollection(File file) throws IOException {
    farmCollection = new ArrayList<Farm>();
    addFile(file);
  }
  
  /**
   * Adds the contents of a new CSV to this FarmCollection.
   * @param file the CSV to add the data for to this FarmCollection.
   * @throws IOException if the file cannot be parsed
   */
  public void addFile(File file) throws IOException {
    FarmParser fp = new FarmParser(file);
    List<MilkStat> milkList = fp.parseFile();
    
    for (MilkStat ms : milkList) {
      addMilkStat(ms);
    }
  }
  
  /**
   * Adds the specified MilkStat, sorting it into the correct Farm and then FarmYear.
   * @param ms the MilkStat to be added
   */
  public void addMilkStat(MilkStat ms) {
    String milkId = ms.getFarmId();
    Farm addToFarm = getFarm(milkId); // find the farm if it exists
    
    if (addToFarm == null) { // if it, doesn't create it
      addToFarm = new Farm(milkId);
      addFarm(addToFarm);
    }
    addToFarm.addMilkStat(ms); // note this method already finds the correct FarmYear
  }
  
  /**
   * Add a specified Farm to this FarmCollection. Two farms cannot share the same name.
   * @param farm
   * @throws IllegalArgumentException if a farm with the same name already exists
   */
  public void addFarm(Farm farm) {
    if (getFarm(farm.getFarmId()) != null) {
      throw new IllegalArgumentException("A Farm with the same name already exists.");
    }
    farmCollection.add(farm);
  }
  
  /**
   * Returns the Farm that is represented by the specified farm ID.
   * @param farmId the name of the Farm to be found
   * @return the Farm represented by the specified ID
   */
  public Farm getFarm(String farmId) {
    for (Farm f : farmCollection) {
      if (f.getFarmId().equals(farmId)) {
        return f;
      }
    }
    return null;
  }
  
  /**
   * Returns the collection of FarmYears represented by this FarmCollection.
   * @return collection of FarmYears
   */
  public ArrayList<Farm> getFarmCollection() {
    return farmCollection;
  }
  
  /**
   * Returns the weight of every MilkStat at every Farm represented by this FarmCollection.
   * @return the weight of every MilkStat
   */
  public int sumOfAllFarmWeights() {
    int weightSum = 0;
    for (Farm f : farmCollection) {
      weightSum += f.sumOfAllWeights();
    }
    return weightSum;
  }
  
  /**
   * Returns a String representation of this FarmCollection.
   * @return a String representation of this FarmCollection
   */
  @Override
  public String toString() {
    String str = "";
    for (Farm f : farmCollection) {
      str += f.getFarmId() + ":\n";
      str += f.toString();
      str += "\n";
    }
    return str;
  }
}
