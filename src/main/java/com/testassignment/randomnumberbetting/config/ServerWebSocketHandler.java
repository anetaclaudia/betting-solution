package com.testassignment.randomnumberbetting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testassignment.randomnumberbetting.dto.BetRequest;
import com.testassignment.randomnumberbetting.service.RandomNumberBettingService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class ServerWebSocketHandler extends TextWebSocketHandler {

  private final RandomNumberBettingService service = new RandomNumberBettingService();

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String request = message.getPayload();
    ObjectMapper objectMapper = new ObjectMapper();
    BetRequest betRequest = objectMapper.readValue(request, BetRequest.class);

    session.sendMessage(
        new TextMessage(objectMapper.writeValueAsString(service.betRandomNumber(betRequest)))
    );
  }
}
