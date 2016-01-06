package com.shippable.exception;

public class DependentServiceException extends RuntimeException {

  private static final long serialVersionUID = 875469505551159620L;

  private String serviceName;

  private Object[] messagePositionalArgs;

  private Throwable exception;

  public DependentServiceException(String serviceName, Throwable underlyingException,
      Object... messagePositionalArgs) {
    super(underlyingException);
    this.messagePositionalArgs = messagePositionalArgs;
    this.serviceName = serviceName;
  }

  public DependentServiceException(String serviceName, Object... messagePositionalArgs) {
    this.messagePositionalArgs = messagePositionalArgs;
    this.serviceName = serviceName;
  }

  public Object[] getMessagePositionalArgs() {
    return messagePositionalArgs;
  }

  public void setMessagePositionalArgs(Object[] messagePositionalArgs) {
    this.messagePositionalArgs = messagePositionalArgs;
  }

  public Throwable getException() {
    return exception;
  }

  public void setException(Throwable exception) {
    this.exception = exception;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

}
