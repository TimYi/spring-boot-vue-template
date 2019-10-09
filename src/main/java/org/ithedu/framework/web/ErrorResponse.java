/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.framework.web;

import java.util.ArrayList;
import java.util.List;

import org.ithedu.utils.SnakeJsonSerializer;

import com.google.common.base.Throwables;

public class ErrorResponse {

  private String code;
  private String message;
  private List<String> stackTraces = new ArrayList<>();

  public static ErrorResponse create(Exception e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.message = e.getMessage();
    String stackTrace = Throwables.getStackTraceAsString(e);
    errorResponse.stackTraces.add(stackTrace);
    if (e instanceof ApplicationException) {
      ApplicationException applicationException = (ApplicationException) e;
      errorResponse.setCode(applicationException.getCode());
      if (applicationException.getOldStackTraces() != null) {
        errorResponse.stackTraces.addAll(applicationException.getOldStackTraces());
      }
    }
    return errorResponse;
  }

  public static ErrorResponse create(Exception e, String code, List<String> preStackTraces) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.message = e.getMessage();
    String stackTrace = Throwables.getStackTraceAsString(e);
    errorResponse.stackTraces.add(0, stackTrace);
    if (e instanceof ApplicationException) {
      ApplicationException applicationException = (ApplicationException) e;
      errorResponse.setCode(applicationException.getCode());
    }
    return errorResponse;
  }

  public static ErrorResponse create(String code, String message) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.code = code;
    errorResponse.message = message;
    return errorResponse;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getStackTraces() {
    return stackTraces;
  }

  public void setStackTraces(List<String> stackTraces) {
    this.stackTraces = stackTraces;
  }

  public String toJson() {
    return SnakeJsonSerializer.serialize(this);
  }
}
