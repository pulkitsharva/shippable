package com.shippable.dto;

import java.util.List;

public class GitHubResponse {

  private List<Issue> issues;

  public List<Issue> getIssues() {
    return issues;
  }

  public void setIssues(List<Issue> issues) {
    this.issues = issues;
  }
  
}
