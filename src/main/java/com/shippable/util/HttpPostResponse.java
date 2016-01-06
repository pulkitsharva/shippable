package com.shippable.util;

import org.springframework.http.HttpStatus;

public class HttpPostResponse {
  
  private String response;
  private HttpStatus status;

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

}
