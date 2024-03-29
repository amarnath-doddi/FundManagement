package com.example.fund.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fund.dto.UserDTO;
import com.example.fund.exception.UserNotfoundException;
import com.example.fund.service.UserRegistrationServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserRegistrationControllerTest {
	@Mock
	private UserRegistrationServiceImpl registrationServiceImpl;
	@InjectMocks
	private UserRegistrationController userRegistrationController;
	
	private static List<UserDTO> users;
	private static UserDTO user;
	private static UserDTO user1;
	@BeforeAll
	public static void setUp() {
		LocalDate date = LocalDate.now();
		LocalDate date1 = LocalDate.now();
		user = new UserDTO();
		user.setFirstName("Amar");
		user.setLastName("Doddi");
		user.setEmail("amarnath.doddi@hcl.com");
		user.setPhone("987654321");
		user.setLoginId("amardoddi");
		user.setPassword("test");
		user.setLastUpdated(date);
		
		user1 = new UserDTO();
		user1.setFirstName("Amar1");
		user1.setLastName("Doddi1");
		user1.setEmail("amarnath1.doddi@hcl.com");
		user1.setPhone("9876543211");
		user1.setLoginId("amardoddi1");
		user1.setPassword("test1");
		user1.setLastUpdated(date1);
		
		users = new ArrayList<>();
		users.add(user);
		users.add(user1);
		
		
	}
	
	@Test
	@DisplayName("Get all users test")
	void testGetAllUsers() {
		when(registrationServiceImpl.getUsers()).thenReturn(users);
		
		List<UserDTO> persistedUsers = userRegistrationController.getUsers().getBody();
		
		verify(registrationServiceImpl).getUsers();
		
		assertEquals(users, persistedUsers);
	}
	
	@Test
	@DisplayName("Get user by id test")
	void testGetUserById() {
		when(registrationServiceImpl.getUser(any(Long.class))).thenReturn(user);
		
		UserDTO persistedUser = userRegistrationController.getUser(1L).getBody();
		
		verify(registrationServiceImpl).getUser(1L);
		
		assertEquals(user, persistedUser);
	}
	
	@Test
	@DisplayName("Get User by id: Negative Scenario")
	void testGetUserByIdNotFound() {
		//context
		when(registrationServiceImpl.getUser(5L)).thenThrow(UserNotfoundException.class);
		
		//event
		//outcome
		assertThrows(UserNotfoundException.class, ()->userRegistrationController.getUser(5L));
	}
	
	@Test
	@DisplayName("Update user test")
	void testUpdateUser() {
		when(registrationServiceImpl.updateUser(any(UserDTO.class))).thenAnswer(i -> {
			user.setId(1000L);
			UserDTO user = i.getArgument(0);
			user.setId(1000L);
			user.setFirstName("Test");
			return user;
		});
		
		UserDTO persistedUser = userRegistrationController.updateUser(user).getBody();
		
		assertEquals("Test", persistedUser.getFirstName());
	}
	
	@Test
	@DisplayName("Delete User Test")
	void testDeleteUser() {
		when(registrationServiceImpl.deleteUser(1L)).thenReturn(true);

		boolean isDeleted = userRegistrationController.deleteUser(1L).getBody();

	    verify(registrationServiceImpl, times(1)).deleteUser(1L);
	    
	    assertTrue(isDeleted);
	}
	
	@Test
	@DisplayName("Save user test")
	void testSaveUser() {
		when(registrationServiceImpl.createUser(any(UserDTO.class))).thenAnswer(i -> {
			UserDTO user = i.getArgument(0);
			user.setId(1L);
			return user;
		});
		
		UserDTO persistedUser = userRegistrationController.createUser(user).getBody();
		
		assertEquals(user, persistedUser);
	}
	
	@Test
	@DisplayName("Negitive Senario:Save User with firstname lessthan 2")
	void testCreateUserFirstNameLessthan2(){
		user.setFirstName("D");
		when(registrationServiceImpl.createUser(user)).thenThrow(ConstraintViolationException.class);
		
		assertThrows(ConstraintViolationException.class, ()->registrationServiceImpl.createUser(user));
	}
	
	@Test
	@DisplayName("Negitive Senario:Save User with lastname lessthan 2")
	void testCreateUserLastNameLessthan2(){
		user.setLastName("D");
		when(registrationServiceImpl.createUser(user)).thenThrow(ConstraintViolationException.class);
		
		assertThrows(ConstraintViolationException.class, ()->registrationServiceImpl.createUser(user));
	}
	
	@Test
	@DisplayName("Negitive Senario:Save User with invalid email ")
	void testCreateUserWithInvalidEmail(){
		user.setEmail("test");
		when(registrationServiceImpl.createUser(user)).thenThrow(ConstraintViolationException.class);
		
		assertThrows(ConstraintViolationException.class, ()->registrationServiceImpl.createUser(user));
	}
}
