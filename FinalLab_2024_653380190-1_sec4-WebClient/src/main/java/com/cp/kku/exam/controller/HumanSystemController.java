package com.cp.kku.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HumanSystemController {
	
	@GetMapping("/")
	public String getRoot() {
		return "index";
	}
}
