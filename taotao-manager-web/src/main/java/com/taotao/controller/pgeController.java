package com.taotao.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.stereotype.Controller;
 

@Controller
public class pgeController {
	@RequestMapping("/")
	public String showIndex() {
		return"index";
	}
	
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
	return page;
	}
}
