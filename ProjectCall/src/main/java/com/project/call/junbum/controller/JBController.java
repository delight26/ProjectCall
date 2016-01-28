package com.project.call.junbum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.call.junbum.service.JBService;

@Controller
public class JBController {
	
	@Autowired
	private JBService jBService;
	
	public void setjBService(JBService jBService) {
		this.jBService = jBService;
	}
	
	@RequestMapping(value="loginform")
	public String loginForm(){
		return "member/login";
	}
}
