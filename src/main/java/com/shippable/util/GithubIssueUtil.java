package com.shippable.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shippable.dto.Issue;
import com.shippable.dto.IssueResponse;

@Component
public class GithubIssueUtil {

  public IssueResponse createResponse(List<Issue> totalIssues) {
    IssueResponse issueResponse = new IssueResponse();
    Date sevenDaysOld = dateBeforeCurrentDay(7);
    Date oneDayOld = dateBeforeCurrentDay(1);
    Integer issuesMoreThan24hr=new Integer(0);
    Integer issuesIn24hr=new Integer(0);
    issueResponse.setTotalissues(totalIssues.size());
    for (Issue issue : totalIssues) {
      if (issue.getCreatedAt().compareTo(sevenDaysOld) >= 0
          && issue.getCreatedAt().compareTo(oneDayOld) <= 0) {
        issuesMoreThan24hr++;
      } else if (oneDayOld.compareTo(issue.getCreatedAt()) <= 0) {
        issuesIn24hr++;
      }
    }
    issueResponse.setIssuesMoreThan24hr(issuesMoreThan24hr);
    issueResponse.setIssuesIn24hr(issuesIn24hr);
    issueResponse.setIssuesMoreThan7days(issueResponse.getTotalissues()
        - issueResponse.getIssuesMoreThan24hr() - issueResponse.getIssuesIn24hr());
    return issueResponse;
  }

  public  Date dateBeforeCurrentDay(int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DAY_OF_MONTH, -day);
    return cal.getTime();
  }

}
