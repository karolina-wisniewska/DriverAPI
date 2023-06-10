package pl.coderslab.driver.util;

public class IndexCalculator {
  public long calculateIndex(int weekNumber, int year, long numberOfAdvices){
    return weekNumber > numberOfAdvices ? ((weekNumber % numberOfAdvices)+1) : (weekNumber * ((year % (numberOfAdvices/weekNumber))+1));
  }
}
