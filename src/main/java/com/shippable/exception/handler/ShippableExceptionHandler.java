package com.shippable.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shippable.exception.ClientException;
import com.shippable.exception.DependentServiceException;
import com.shippable.exception.NotFoundException;
import com.shippable.exception.ShippableErrorInfo;

@ControllerAdvice(basePackages="com.shippable.controller")
public class ShippableExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ShippableExceptionHandler.class);

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ShippableErrorInfo> handleNotFound(NotFoundException e) {
    ShippableErrorInfo error = createError(e);
    return new ResponseEntity<ShippableErrorInfo>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public void handleNotReadable(HttpMessageNotReadableException e) {
    LOG.error("HttpMessageNotReadableException", e);
  }

  @ExceptionHandler({DependentServiceException.class})
  @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
  public void handleDependentService(DependentServiceException e) {
    LOG.error("Dependent Service Exception:" + e.getServiceName(), e);
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public void handleExceptions(Exception e) {
    LOG.error("Exception", e);
  }

  private ShippableErrorInfo createError(ClientException e) {
    ShippableErrorInfo error = new ShippableErrorInfo();
    StringBuffer message = new StringBuffer();
    if (null != e.getMessage()) {
      message.append(e.getMessage());
    }
    if (null != e.getErrorCode()) {
      message.append(e.getErrorCode().getMessage());
    }
    if (null != e.getMessagePositionalArgs()) {
      for (Object object : e.getMessagePositionalArgs()) {
        message.append(" ");
        message.append(object);
      }
    }
    error.setMessage(message.toString());
    LOG.error(message.toString(), e);
    return error;
  }

}
