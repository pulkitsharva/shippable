package com.shippable.dto;

import javax.validation.constraints.NotNull;

public class IssueRequest {
  @NotNull
  private String repoUrl;

  public String getRepoUrl() {
    return repoUrl;
  }

  public void setRepoUrl(String repoUrl) {
    this.repoUrl = repoUrl;
  }
  
  
}
