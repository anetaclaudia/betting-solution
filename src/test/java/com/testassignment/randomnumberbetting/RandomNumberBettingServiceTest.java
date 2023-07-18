package com.testassignment.randomnumberbetting;

import static com.testassignment.randomnumberbetting.util.randomNumber.RandomNumbertUtils.generateRandomBet;
import static com.testassignment.randomnumberbetting.util.randomNumber.RandomNumbertUtils.generateRandomNumber;

import com.testassignment.randomnumberbetting.dto.BetRequest;
import com.testassignment.randomnumberbetting.dto.BetResponse;
import com.testassignment.randomnumberbetting.service.RandomNumberBettingService;
import com.testassignment.randomnumberbetting.util.randomNumber.RandomNumbertUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class RandomNumberBettingServiceTest {

  private final RandomNumberBettingService service = new RandomNumberBettingService();

  @Test
  public void shouldReturnWin() {
    BetRequest request = new BetRequest(50, BigDecimal.valueOf(40.5));
    BetResponse expected = new BetResponse(BigDecimal.valueOf(80.19));

    try (MockedStatic<RandomNumbertUtils> utilities = Mockito.mockStatic(RandomNumbertUtils.class)) {
      utilities.when(RandomNumbertUtils::generateRandomNumber).thenReturn(40);
      BetResponse actual = service.betRandomNumber(request);
      assert (expected.win().equals(actual.win()));
    }

  }

  @Test
  public void shouldNotReturnWin_whenRandomNumberIsHundred() {
    BetRequest request = new BetRequest(100, BigDecimal.valueOf(40.5));
    BetResponse expected = new BetResponse(BigDecimal.ZERO);

    try (MockedStatic<RandomNumbertUtils> utilities = Mockito.mockStatic(RandomNumbertUtils.class)) {
      utilities.when(RandomNumbertUtils::generateRandomNumber).thenReturn(40);
      BetResponse actual = service.betRandomNumber(request);
      assert (expected.win().equals(actual.win()));
    }
  }

  @Test
  public void shouldNotReturnWin_whenRequestRandomNumberIsSmallerThanServers() {
    BetRequest request = new BetRequest(50, BigDecimal.valueOf(40.5));
    BetResponse expected = new BetResponse(BigDecimal.ZERO);

    try (MockedStatic<RandomNumbertUtils> utilities = Mockito.mockStatic(RandomNumbertUtils.class)) {
      utilities.when(RandomNumbertUtils::generateRandomNumber).thenReturn(70);
      BetResponse actual = service.betRandomNumber(request);
      assert (expected.win().equals(actual.win()));
    }
  }

  @Test
  public void shouldPlayOneMillionRounds() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(24);

    AtomicReference<BigDecimal> betAmount = new AtomicReference<>(BigDecimal.ZERO);
    AtomicReference<BigDecimal> winAmount = new AtomicReference<>(BigDecimal.ZERO);
    AtomicInteger wins = new AtomicInteger();
    for (int i = 0; i < 1000000; i++) {
      executor.execute(() -> {
        BetRequest randomRequest = new BetRequest(generateRandomNumber(), generateRandomBet());
        BetResponse response = service.betRandomNumber(randomRequest);
        if (!Objects.equals(response.win(), BigDecimal.ZERO)) {
          wins.incrementAndGet();
        }
        while (true) {
          BigDecimal oldValue = betAmount.get();
          BigDecimal newValue = oldValue.add(randomRequest.bet());
          if (betAmount.compareAndSet(oldValue, newValue)) {
            break;
          }
        }

        while (true) {
          BigDecimal oldValue = winAmount.get();
          BigDecimal newValue = oldValue.add(response.win());
          if (winAmount.compareAndSet(oldValue, newValue)) {
            break;
          }
        }
      });
    }

    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.MINUTES);

    // RTP = winnings / bets * 100
    BigDecimal rtp = (winAmount.get().divide(betAmount.get(), 2, RoundingMode.HALF_EVEN)).multiply(
            BigDecimal.valueOf(100))
        .setScale(2, RoundingMode.HALF_EVEN);
    System.out.printf("User has won %s games out of 1 000 000%n", wins.get());
    System.out.printf("User's RTP is %s %%%n", rtp);
  }

}
