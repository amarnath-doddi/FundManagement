package com.example.fund.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fund.entity.User;
import com.example.fund.repository.LoginRepository;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {
	@Mock
	private LoginRepository loginRepository;
	@InjectMocks
	private LoginServiceImpl loginServiceImpl;
	
	private static User user;
	
	@BeforeAll
	public static void setUp() {
		LocalDate date = LocalDate.now();
		user = new User();
		user.setFirstName("Amar");
		user.setLastName("Doddi");
		user.setEmail("amarnath.doddi@hcl.com");
		user.setPhone("987654321");
		user.setLoginId("amardoddi");
		user.setPassword("test");
		user.setLastUpdated(date);
	}
	
	@Test
	@DisplayName("Athenticate user Login")
	public void loginTest() {
		when(loginRepository.findByLoginIdAndPassword("amardoddi", "test")).thenReturn(this.user);
		
		boolean isSuccessful = loginServiceImpl.login("amardoddi", "test");
		
		assertTrue(isSuccessful);
		
	}
}
