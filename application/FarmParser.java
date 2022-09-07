package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A class for reading the contents of a CSV and parsing them into MilkStats.
 * @author Sean
 */
public class FarmParser {
  private File file; // the file to be parsed by this FarmParser
  private BufferedReader br; // the BufferedReader this FarmParser represents
  
  /**
   * Creates a new FarmParser to parse the given file.
   * @param file the file to be parsed
   * @throws IOException if the file cannot be read
   */
  public FarmParser(File file) throws IOException {
    this.readFile(file);
  }
  
  /**
   * Read a new file into memory.
   * @param file the file to be parsed
   * @throws IOException if the file cannot be read
   */
  public void readFile(File file) throws IOException {
    this.file = file;
    this.br = new BufferedReader(new FileReader(file));
    br.readLine(); // skip the first line, which is useless
  }
  
  /**
   * Parses the file previously read into this FarmParser into a List of MilkStats.
   * @return a List of MilkStats represented in the parsed CSV
   * @throws IOException if the file cannot be parsed
   */
  public List<MilkStat> parseFile() throws IOException {
    List<MilkStat> milkList = new ArrayList<>();
    String milkStr = "";
    while ((milkStr = br.readLine()) != null) {
      milkList.add(strToMilkStat(milkStr));
    }
    return milkList;
  }
  
  /**
   * Get just one MilkStat from the parsed CSV.
   * @return the next MilkStat from the file
   * @throws IOException if the file cannot be parsed
   */
  public MilkStat readNextMilkStat() throws IOException {
    String milkStr = br.readLine();
    return strToMilkStat(milkStr);
  }
  
  /**
   * Converts a String of text into a MilkStat.
   * @param milkStr String of the format "YYYY-MM-DD,farmid,weight"
   * @return a MilkStat with the properties of milkStr
   */
  public static MilkStat strToMilkStat(String milkStr) {
    String[] milkStatArr = milkStr.split(",");
    Calendar dateStr = dateStrToDate(milkStatArr[0]);
    String farmId = milkStatArr[1];
    int weight = Integer.valueOf(milkStatArr[2]);
    
    MilkStat milkStat = new MilkStat(dateStr, farmId, weight);
    return milkStat;
  }
  
  /**
   * Converts a String representing a day in time into a Calendar object.
   * @param dateStr String of the format "YYYY-MM-DD"
   * @return a Calendar with the properties of dateStr
   */
  private static Calendar dateStrToDate(String dateStr) {
    String[] dateStrArr = dateStr.split("-");
    int year = Integer.valueOf(dateStrArr[0]);
    int month = Integer.valueOf(dateStrArr[1]) - 1; // Java months begin at 0 and not 1
    int day = Integer.valueOf(dateStrArr[2]);
    
    Calendar date = new GregorianCalendar(year, month, day);
    return date;
  }
}
