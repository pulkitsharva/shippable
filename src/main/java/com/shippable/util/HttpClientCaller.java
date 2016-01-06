package com.shippable.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.shippable.exception.DependentServiceException;
import com.shippable.exception.ErrorCode;
import com.shippable.exception.NotFoundException;

@Component
public class HttpClientCaller {


  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientCaller.class);

  public String httpGetApiCall(String serviceHostname, String requestUrl, String userAccesToken,
      Map<String, Object> urlParams, Boolean throw503) {
    String output = null;
    HttpClient httpclient = HttpClientBuilder.create().build();
    List<NameValuePair> params = new LinkedList<NameValuePair>();
    Iterator it = urlParams.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      params.add(new BasicNameValuePair(pair.getKey().toString(), pair.getValue().toString()));
    }
    String paramString = URLEncodedUtils.format(params, "utf-8");


    HttpGet httpget = new HttpGet(serviceHostname + requestUrl + "/issues?" + paramString);
    LOGGER.info("Service : " + serviceHostname + requestUrl);
    HttpResponse response = null;

    try {
      response = httpclient.execute(httpget);
    } catch (Exception e) {
      LOGGER.error("Error while getting response");
      return null;
    }

    LOGGER.info("Service Response:" + response.getStatusLine().getStatusCode());
    if (response.getStatusLine().getStatusCode() == HttpStatus.NOT_FOUND.value()) {
      throw new NotFoundException(ErrorCode.NOT_FOUND, "https://github.com" + requestUrl);
    }
    if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value() && throw503) {
      throw new DependentServiceException(requestUrl, response.getStatusLine().getStatusCode());
    }
    try {
      output = EntityUtils.toString(response.getEntity());
    } catch (Exception e) {
      LOGGER.error("Error while parsig response ", response.getEntity());
    }

    return output;
  }

  public String httpGetApiCall(String serviceHostname, String requestUrl,
      Map<String, Object> urlParam, String userAccesToken) {
    return httpGetApiCall(serviceHostname, requestUrl, userAccesToken, urlParam, true);
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
