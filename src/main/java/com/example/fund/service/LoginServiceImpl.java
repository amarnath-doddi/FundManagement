package com.example.fund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fund.entity.User;
import com.example.fund.repository.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService {
	
	private LoginRepository loginRepository;
	
	@Autowired
	public void setLoginRepository(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}
	public LoginRepository getLoginRepository() {
		return loginRepository;
	}
	@Override
	public boolean login(String loginId, String password) {
		User user = loginRepository.findByLoginIdAndPassword(loginId,password);
		if(user!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean resetPassword(String userId, String password) {
		User user = loginRepository.findByLoginId(userId);
		user.setPassword(password);
		User updatedUser = loginRepository.saveAndFlush(user);
		if(user.getLoginId().equals(userId) && user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean resetUserId(String userId, String newUserId) {
		User user = loginRepository.findByLoginId(userId);
		user.setLoginId(newUserId);
		User updatedUser = loginRepository.saveAndFlush(user);
		if(user.getLoginId().equals(newUserId)) {
			return true;
		}
		return false;
	}

}
