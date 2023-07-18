package com.testassignment.randomnumberbetting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testassignment.randomnumberbetting.dto.BetRequest;
import com.testassignment.randomnumberbetting.service.RandomNumberBettingService;
import com.testassignment.randomnumberbetting.util.error.ErrorCode;
import com.testassignment.randomnumberbetting.util.error.GeneralError;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class ServerWebSocketHandler extends TextWebSocketHandler {

  private static final int MAX_RANDOM_NUMBER = 100;
  private static final int MIN_RANDOM_NUMBER = 1;
  private final RandomNumberBettingService service = new RandomNumberBettingService();

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String request = message.getPayload();
    ObjectMapper objectMapper = new ObjectMapper();
    BetRequest betRequest = objectMapper.readValue(request, BetRequest.class);

    if (!isRequestValid(betRequest)) {
      GeneralError errorResponse = new GeneralError(ErrorCode.INVALID_REQUEST,
          "Random number must be in range 1-100. Bet must be at least 1.");

      session.sendMessage(
          new TextMessage(objectMapper.writeValueAsString(errorResponse))
      );
    } else {
      session.sendMessage(
          new TextMessage(objectMapper.writeValueAsString(service.betRandomNumber(betRequest)))
      );
    }
  }

  private boolean isRequestValid(BetRequest request) {
    boolean isRandomNumberInRange = request.randomNumber() <= MAX_RANDOM_NUMBER
        || request.randomNumber() >= MIN_RANDOM_NUMBER;
    boolean isBetPositive = request.bet().compareTo(BigDecimal.ONE) > 0;
    return isRandomNumberInRange && isBetPositive;
  }
}
