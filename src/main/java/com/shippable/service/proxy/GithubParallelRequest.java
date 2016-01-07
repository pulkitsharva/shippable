package com.shippable.service.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import com.shippable.dto.Issue;

public class GithubParallelRequest implements Callable<Integer> {

  private GithubServiceProxy githubServiceProxy;
  private HashMap<String, Object> urlParams;
  private String repoUrl;

  public GithubParallelRequest(GithubServiceProxy githubServiceProxy,
      HashMap<String, Object> urlParams, String repoUrl) {
    super();
    this.githubServiceProxy = githubServiceProxy;
    this.urlParams = urlParams;
    this.repoUrl = repoUrl;
  }


  @Override
  public Integer call() throws Exception {
    List<Issue> issues = githubServiceProxy.getAllIssue(null,repoUrl, urlParams);
    if (issues != null)
      return issues.size();
    else
      return 0;
  }

}
