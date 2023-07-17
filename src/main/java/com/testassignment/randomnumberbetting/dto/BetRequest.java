package com.testassignment.randomnumberbetting.dto;

import jakarta.validation.constraints.Max;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record BetRequest(
    @Min(1) @Max(100) Integer randomNumber,
    @Min(1) BigDecimal bet) {

}
