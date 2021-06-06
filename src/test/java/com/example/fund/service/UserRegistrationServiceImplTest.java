package com.example.fund.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fund.dto.UserDTO;
import com.example.fund.entity.User;
import com.example.fund.repository.AccountRepository;
import com.example.fund.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class UserRegistrationServiceImplTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private UserRegistrationServiceImpl userRegistrationServiceImpl;
	
	private static UserDTO user;
	
	private static UserDTO userPersisted;
	
	@BeforeAll
	public static void setUp() {
		LocalDate date = LocalDate.now();
		user = new UserDTO();
		user.setFirstName("Amar");
		user.setLastName("Doddi");
		user.setEmail("amarnath.doddi@hcl.com");
		user.setPhone("987654321");
		user.setLoginId("amardoddi");
		user.setPassword("test");
		user.setLastUpdated(date);
		
		userPersisted = new UserDTO();
		userPersisted.setId(1L);
		userPersisted.setFirstName("Amar");
		userPersisted.setLastName("Doddi");
		userPersisted.setEmail("amarnath.doddi@hcl.com");
		userPersisted.setPhone("987654321");
		userPersisted.setLoginId("amardoddi");
		userPersisted.setPassword("test");
		userPersisted.setLastUpdated(date);
	}
	
	@Test
	@DisplayName("Save User Test")
	@Order(1)
	void testCreateUer(){
		//context
		when(userRepository.save(any(User.class))).thenAnswer(i -> {
			User user = i.getArgument(0);
			user.setId(1L);
			return user;
		});
		//event
		UserDTO user = userRegistrationServiceImpl.createUser(this.user);
		//outcome
		assertEquals(user,userPersisted);
	}
	
	@Test
	@DisplayName("Update User Test")
	@Order(2)
	void testUpdateUser() {
		when(userRepository.saveAndFlush(any(User.class))).thenAnswer(i -> {
			User user = i.getArgument(0);
			user.setId(1L);
			user.setLoginId("temploginid");
			return user;
		});
		UserDTO user = userRegistrationServiceImpl.updateUser(this.user);
		assertEquals(user.getLoginId(), "temploginid");
	}
	
	@Test
	@Order(3)
	@DisplayName("Delete User Test")
	void testDeleteUser() {
		//when(userRepository.findById(1L)).thenReturn(Optional.of(user.getUser()));

		assertTrue(userRegistrationServiceImpl.deleteUser(1L));

	    //verify(userRepository, times(1)).delete(user.getUser());
	}
}
