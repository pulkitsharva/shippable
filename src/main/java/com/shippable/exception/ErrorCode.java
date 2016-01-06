package com.shippable.exception;

public enum ErrorCode {
  INVALID_REQUEST("Invalid Request."),
  NOT_FOUND("Unable to find a repo");

  private String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getValue() {
    return this.message;
  }

}
