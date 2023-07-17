package com.testassignment.randomnumberbetting.util;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.uncapitalize;

import com.testassignment.randomnumberbetting.util.error.ErrorCode;
import com.testassignment.randomnumberbetting.util.error.ErrorResponse;
import com.testassignment.randomnumberbetting.util.error.FieldError;
import com.testassignment.randomnumberbetting.util.error.GeneralError;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandlerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    log.warn(exception.getMessage());
    return validationResponse(fieldErrorList(exception.getBindingResult()));
  }

  private static ErrorResponse validationResponse(List<FieldError> fieldErrors) {
    return new ErrorResponse(
        new GeneralError(ErrorCode.INVALID_REQUEST, "Validation has failed"),
        fieldErrors);
  }


  private List<FieldError> fieldErrorList(BindingResult bindingResult) {
    return bindingResult.getFieldErrors().stream()
        .map(fieldError -> new FieldError(
            fieldError.getField(),
            uncapitalize(Objects.requireNonNull(fieldError.getCode())),
            fieldError.getDefaultMessage())
        )
        .collect(Collectors.toList());
  }
}
