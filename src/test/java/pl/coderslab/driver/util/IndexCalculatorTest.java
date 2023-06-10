package pl.coderslab.driver.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexCalculatorTest {

  @Test
  void calculateIndexWhenWeeksGreaterThanAdvicesNumber() {
    //given
    int weekNumber = 25;
    int year = 2023;
    int numberOfAdvices = 3;

    //when
    IndexCalculator indexCalculator = new IndexCalculator();
    long expected = 2;

    //then
    assertEquals(expected, indexCalculator.calculateIndex(weekNumber, year, numberOfAdvices));
  }

  @Test
  void calculateIndexWhenWeeksEqualToAdvicesNumber() {
    //given
    int weekNumber = 25;
    int year = 2023;
    int numberOfAdvices = 25;

    //when
    IndexCalculator indexCalculator = new IndexCalculator();
    long expected = 25;

    //then
    assertEquals(expected, indexCalculator.calculateIndex(weekNumber, year, numberOfAdvices));
  }

  @Test
  void calculateIndexWhenWeeksSmallerThanAdvicesNumberRatio1() {
    //given
    int weekNumber = 25;
    int year = 2023;
    int numberOfAdvices = 40;

    //when
    IndexCalculator indexCalculator = new IndexCalculator();
    long expected = 25;

    //then
    assertEquals(expected, indexCalculator.calculateIndex(weekNumber, year, numberOfAdvices));
  }

  @Test
  void calculateIndexWhenWeeksSmallerThanAdvicesNumberRatio3() {
    //given
    int weekNumber = 25;
    int year = 2023;
    int numberOfAdvices = 80;

    //when
    IndexCalculator indexCalculator = new IndexCalculator();
    long expected = 50;

    //then
    assertEquals(expected, indexCalculator.calculateIndex(weekNumber, year, numberOfAdvices));
  }
}