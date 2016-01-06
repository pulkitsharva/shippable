package com.shippable.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shippable.dto.IssueRequest;
import com.shippable.service.GithubService;

 
/**
 * Handles requests for the Employee service.
 */
@Controller
public class GithubController {
     @Autowired
     private GithubService githubService;
   
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Object> getEmployee() {
        IssueRequest issueRequest=new IssueRequest();
        githubService.getAllIssue(issueRequest);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
    @RequestMapping(value = "/issues", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> getIssues(@RequestBody @Valid IssueRequest issueRequest) {
      
        String test="getting data";
        return new ResponseEntity<Object>(test,HttpStatus.OK);
    }
     
}