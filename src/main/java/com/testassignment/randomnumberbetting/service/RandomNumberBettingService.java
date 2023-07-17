package com.testassignment.randomnumberbetting.service;

import com.testassignment.randomnumberbetting.dto.BetRequest;
import com.testassignment.randomnumberbetting.dto.BetResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomNumberBettingService {

  private final BigDecimal NINETY_NINE = BigDecimal.valueOf(99);
  private final BigDecimal HUNDRED = BigDecimal.valueOf(100);

  public BetResponse betRandomNumber(BetRequest request) {
    // Formula for win: bet * (99 / (100 - number))
    BigDecimal win = request.bet()
        .multiply(
            NINETY_NINE.divide(HUNDRED.subtract(BigDecimal.valueOf(request.randomNumber())))
        ).setScale(2, RoundingMode.HALF_EVEN);
    return new BetResponse(win);
  }

}
