package com.shippable.util;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.shippable.exception.DependentServiceException;

@Component
public class HttpClientCaller {


  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientCaller.class);

  public String httpGetApiCall(String serviceHostname, String requestUrl, String userAccesToken,
      Boolean throw503) {
    String output = null;
    HttpClient httpclient = HttpClientBuilder.create().build();

    HttpGet httpget = new HttpGet(serviceHostname + requestUrl);
    //RequestConfig requestConfig =
        RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
    //httpget.setConfig(requestConfig);
    LOGGER.info("Service : " + serviceHostname + requestUrl);
    HttpResponse response = null;
    try {
      response = httpclient.execute(httpget);
      LOGGER.info("Service Response:" + response.getStatusLine().getStatusCode());
      if (response.getStatusLine().getStatusCode() == HttpStatus.NOT_FOUND.value()) {
        return null;
      }
      if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value() && throw503) {
        throw new DependentServiceException(requestUrl, response.getStatusLine().getStatusCode());
      }
      output = EntityUtils.toString(response.getEntity());
    } catch (Exception e) {
      throw new DependentServiceException(requestUrl, e);
    }
    return output;
  }

  public String httpGetApiCall(String serviceHostname, String requestUrl, String userAccesToken) {
    return httpGetApiCall(serviceHostname, requestUrl, userAccesToken, true);
  }


  public HttpPostResponse httpPOSTApiCall(String serviceHostname, String requestUrl,
      Map<String, String> requestParams, String helpchatAuthToken, String requestBody,
      Map<String, String> requestHeaders) {
    String output = null;

    HttpPostResponse httpPostResponse = new HttpPostResponse();
    HttpClient httpclient =
        HttpClientBuilder.create().setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
            .build();
    URIBuilder uriBuilder = null;
    try {
      if (null == serviceHostname) {
        // default hostName
      }
      uriBuilder = new URIBuilder(serviceHostname + requestUrl);
      if (null != requestParams && requestParams.size() != 0) {
        for (String param : requestParams.keySet()) {
          uriBuilder.addParameter(param, requestParams.get(param));
        }
      }
      HttpPost httpPost = new HttpPost(uriBuilder.build());
      LOGGER.info("Service : " + serviceHostname + requestUrl);
      if (null != requestBody) {
        StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
      }
      HttpResponse response = null;
      response = httpclient.execute(httpPost);
      LOGGER.info("Service Response:" + response.getStatusLine().getStatusCode() + ",requestBody:"
          + requestBody);
      output = EntityUtils.toString(response.getEntity());
      httpPostResponse.setStatus(HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
      if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()
          || response.getStatusLine().getStatusCode() == HttpStatus.CREATED.value()) {
        httpPostResponse.setResponse(output);
      }
      if (!(response.getStatusLine().getStatusCode() == HttpStatus.OK.value() || response
          .getStatusLine().getStatusCode() == HttpStatus.CREATED.value())) {
        LOGGER.error("Bad Response Code :" + requestUrl + " "
            + response.getStatusLine().getStatusCode() + output);

      }
    } catch (Exception e) {
      LOGGER.error("Error in call to Service:", e);
    }
    return httpPostResponse;
  }

}
