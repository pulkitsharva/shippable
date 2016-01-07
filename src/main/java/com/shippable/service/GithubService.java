package com.shippable.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shippable.dto.Issue;
import com.shippable.dto.IssueRequest;
import com.shippable.dto.IssueResponse;
import com.shippable.service.proxy.GithubParallelRequest;
import com.shippable.service.proxy.GithubServiceProxy;
import com.shippable.util.GithubIssueUtil;

@Service
public class GithubService {

  @Autowired
  private GithubServiceProxy githubServiceProxy;

  @Autowired
  private GithubIssueUtil issueUtil;

  private static final Logger LOG = LoggerFactory.getLogger(GithubService.class);

  public IssueResponse getAllIssue(IssueRequest issueRequest) {
    IssueResponse issueResponse = null;
    HashMap<String, Object> urlParam = new HashMap<String, Object>();
    urlParam.put("state", "open");
    List<Issue> totalIssues =
        githubServiceProxy.getAllIssue(issueRequest.getRepoUrl(),getUserAndRepo(issueRequest), urlParam);
    if (totalIssues != null && !totalIssues.isEmpty()) {
      issueResponse = issueUtil.createResponse(totalIssues);
    }
    return issueResponse;
  }

  private String getUserAndRepo(IssueRequest issueRequest) {
    String url = "";
    String[] input = issueRequest.getRepoUrl().split(".com");
    if (input != null) {
      url = input[input.length - 1];
    }
    return url;

  }

}
