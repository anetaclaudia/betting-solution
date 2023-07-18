package com.testassignment.randomnumberbetting.service;

import com.testassignment.randomnumberbetting.dto.BetRequest;
import com.testassignment.randomnumberbetting.dto.BetResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class RandomNumberBettingServiceTest {

  private final RandomNumberBettingService service = new RandomNumberBettingService();

  @Test
  public void shouldReturnWin() {
    BetRequest request = new BetRequest(50, BigDecimal.valueOf(40.5));
    BetResponse expected = new BetResponse(BigDecimal.valueOf(80.19));

    BetResponse actual = service.betRandomNumber(request);

    assert (expected.win().equals(actual.win()));
  }

  @Test
  public void shouldNotReturnWin_whenRandomNumberIs100() {
    BetRequest request = new BetRequest(100, BigDecimal.valueOf(40.5));
    BetResponse excpected = new BetResponse(BigDecimal.ZERO);

    BetResponse actual = service.betRandomNumber(request);

    assert (excpected.win().equals(actual.win()));
  }

}
