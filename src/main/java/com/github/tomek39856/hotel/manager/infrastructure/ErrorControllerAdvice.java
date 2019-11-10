package com.github.tomek39856.hotel.manager.infrastructure;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorControllerAdvice {
  @ExceptionHandler
  @ResponseBody
  public String error(Exception e) {
    e.printStackTrace();
    return e.getMessage();
  }
}
