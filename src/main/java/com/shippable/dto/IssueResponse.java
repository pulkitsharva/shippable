package com.shippable.dto;

import java.io.Serializable;

public class IssueResponse implements Serializable {

  private static final long serialVersionUID = 6780988691956731333L;
  private Integer totalissues;
  private Integer issuesIn24hr;
  private Integer issuesMoreThan24hr;
  private Integer issuesMoreThan7days;
  public Integer getTotalissues() {
    return totalissues;
  }
  public void setTotalissues(Integer totalissues) {
    this.totalissues = totalissues;
  }
  public Integer getIssuesIn24hr() {
    return issuesIn24hr;
  }
  public void setIssuesIn24hr(Integer issuesIn24hr) {
    this.issuesIn24hr = issuesIn24hr;
  }
  public Integer getIssuesMoreThan24hr() {
    return issuesMoreThan24hr;
  }
  public void setIssuesMoreThan24hr(Integer issuesMoreThan24hr) {
    this.issuesMoreThan24hr = issuesMoreThan24hr;
  }
  public Integer getIssuesMoreThan7days() {
    return issuesMoreThan7days;
  }
  public void setIssuesMoreThan7days(Integer issuesMoreThan7days) {
    this.issuesMoreThan7days = issuesMoreThan7days;
  }
  
  
}
