package com.testassignment.randomnumberbetting.util.randomNumber;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomNumbertUtils {

  private final Random random = new Random();
  private final int MAX_RANDOM_NUMBER = 100;
  private final int MIN_RANDOM_NUMBER = 1;

  public static int generateRandomNumber() {
    return (int) (Math.random() * (MAX_RANDOM_NUMBER - MIN_RANDOM_NUMBER)) + MIN_RANDOM_NUMBER;
  }

  public static BigDecimal generateRandomBet() {
    return BigDecimal.valueOf(Math.abs(random.nextDouble(1, 100)))
        .setScale(2, RoundingMode.HALF_EVEN);
  }
}
