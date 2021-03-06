package com.shippable.service.proxy;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shippable.dto.Issue;
import com.shippable.util.HttpClientCaller;

@Service
public class GithubServiceProxy {

  @Autowired
  private HttpClientCaller httpClient;

  private ObjectMapper objectMapper = new ObjectMapper();
  
  private String hostname="https://api.github.com/repos";

  private static final Logger LOG = LoggerFactory.getLogger(GithubServiceProxy.class);

  public List<Issue> getAllIssue(String originalUrl,String repoUrl,Map<String,Object> urlParam) {
    List<Issue> issues =null;
      String response =
          httpClient
              .httpGetApiCall(originalUrl,hostname,repoUrl, urlParam,null);
      try {
      issues =
          objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, Issue.class));
    } catch (Exception e) {
      LOG.error("Error while parsing request",e);
    }
    return issues;
  }

}
