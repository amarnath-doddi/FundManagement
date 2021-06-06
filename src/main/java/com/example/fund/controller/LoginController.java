package com.example.fund.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fund.dto.UserDTO;
import com.example.fund.exception.InvalidCredentialsException;
import com.example.fund.service.LoginService;
import com.example.fund.service.UserRegistrationService;

@RestController
@RequestMapping("api/users")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	private LoginService loginService;
	private UserRegistrationService userRegistrationService;
	
	@Autowired
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public LoginService getLoginService() {
		return loginService;
	}
	@Autowired
	public void setUserRegistrationService(UserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}
	public UserRegistrationService getUserRegistrationService() {
		return userRegistrationService;
	}
	@GetMapping("/login")
	public ResponseEntity<Boolean> login(@RequestParam String loginId,@RequestParam String password) throws InvalidCredentialsException{
		UserDTO user = userRegistrationService.findByLoginId(loginId);
		if(user==null) {
			logger.error("Login id is Incorrect!");
			throw new InvalidCredentialsException("Login id is Incorrect!");
		}
		if(!user.getPassword().equals(password)) {
			logger.error("Password is Incorrect!");
			throw new InvalidCredentialsException("Password is Incorrect!");
		}
		logger.info("User logged in successfull!");
		return new ResponseEntity<>(true,HttpStatus.OK);
	}
	
	
}
