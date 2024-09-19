package com.ecom.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


	
	  @GetMapping("/")
	    public String index() {
	        return "index";  // Ensure this corresponds to your index page template
	    }

	    @GetMapping("/signin")
	    public String login() {
	        return "login";  // Ensure this corresponds to your login page template
	    }

	    @GetMapping("/register")
	    public String register() {
	        return "register";  // Ensure this corresponds to your register page template
	    }

	    @GetMapping("/product")
	    public String product() {
	        return "product";  // Ensure this corresponds to your product page template
	    }
	    
}
