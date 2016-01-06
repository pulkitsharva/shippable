package com.shippable.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.shippable.dto.Issue;
import com.shippable.dto.IssueRequest;
import com.shippable.dto.IssueResponse;
import com.shippable.service.proxy.GithubServiceProxy;

@Service
public class GithubService {
  
  @Autowired
  private GithubServiceProxy githubServiceProxy;
  
  @Autowired
  public Executor threadPoolTaskExecutor;
  
  public IssueResponse getAllIssue(IssueRequest issueRequest){
    HashMap<String,Object> urlParam=new HashMap<String,Object>();
    urlParam.put("state", "open");
    List<Issue> totalIssues=githubServiceProxy.getAllIssue(issueRequest.getRepoUrl(),urlParam);
    urlParam.put("since",dateBeforeCurrentDay(1));
    List<Issue> issuesIn24hr=githubServiceProxy.getAllIssue(issueRequest.getRepoUrl(),urlParam);
    urlParam.put("since",dateBeforeCurrentDay(7));
    List<Issue> issuesIn7Days=githubServiceProxy.getAllIssue(issueRequest.getRepoUrl(),urlParam);
    System.out.println(threadPoolTaskExecutor);
    //use callable here
    //public class FacebookThread implements Callable<Integer>
    return null;
  }
  
  public void sendConcurrentRequest(){

    
  }
  public Date dateBeforeCurrentDay(int day){
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DAY_OF_MONTH, -day);
    return cal.getTime();
  }
  
  
}
