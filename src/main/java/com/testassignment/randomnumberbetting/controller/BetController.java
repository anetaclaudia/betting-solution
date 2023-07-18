package com.testassignment.randomnumberbetting.controller;

import com.testassignment.randomnumberbetting.dto.BetRequest;
import com.testassignment.randomnumberbetting.dto.BetResponse;
import com.testassignment.randomnumberbetting.service.RandomNumberBettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BetController {

  private final RandomNumberBettingService service;

  @PostMapping("/bet")
  public BetResponse betRandomNumber(@RequestBody @Valid BetRequest request) {
    return service.betRandomNumber(request);
  }
}
