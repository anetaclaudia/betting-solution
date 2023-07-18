package com.testassignment.randomnumberbetting.service;

import static com.testassignment.randomnumberbetting.util.randomNumber.RandomNumbertUtils.generateRandomNumber;

import com.testassignment.randomnumberbetting.dto.BetRequest;
import com.testassignment.randomnumberbetting.dto.BetResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomNumberBettingService {

  private static final BigDecimal NINETY_NINE = BigDecimal.valueOf(99);
  private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

  public BetResponse betRandomNumber(BetRequest request) {
    Integer randomNumber = generateRandomNumber();
    // Formula for win: bet * (99 / (100 - number))
    BigDecimal win = isRequestRandomNumberBigger(request, randomNumber)
        ? request.bet()
        .multiply(
            NINETY_NINE.divide(HUNDRED.subtract(BigDecimal.valueOf(request.randomNumber())), 2,
                RoundingMode.HALF_EVEN))
        .setScale(2, RoundingMode.HALF_EVEN)
        : BigDecimal.ZERO;
    return new BetResponse(win);
  }

  private static boolean isRequestRandomNumberBigger(BetRequest request, Integer randomNumber) {
    return randomNumber < request.randomNumber() && request.randomNumber() != 100;
  }

}
