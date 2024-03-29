package com.example.fund.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import com.example.fund.dto.AccountDTO;
import com.example.fund.dto.FundTransfer;
import com.example.fund.entity.Account;
import com.example.fund.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class FundTransferServiceImplTest {
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private FundTransferServiceImpl fundTransferServiceImpl;
	
	private static FundTransfer fundTransfer;
	private static AccountDTO account;
	@BeforeAll
	public static void setUp() {
		fundTransfer = new FundTransfer();
		fundTransfer.setAmount(500.00);
		fundTransfer.setBeneficaryId(3001L);
		
		account = new AccountDTO();
		account.setId(1L);
		account.setAccountNumber(3234456L);
		account.setBalance(1000.00);
		account.setUserId(1000L);
		
	}
	@Test
	@DisplayName("Test Fund Transfer")
	@Order(1)
	void testGetUserById() {
		when(accountRepository.findByUserId(any(Long.class))).thenReturn(account.getAccount());
		
		AccountDTO persistedAccount = fundTransferServiceImpl.getByUserId(1000L);
		
		assertEquals(account, persistedAccount);
	}
	
	@Test
	@DisplayName("Update Account Test")
	@Order(2)
	void testUpdateAccount() {
		when(accountRepository.saveAndFlush(any(Account.class))).thenAnswer(i -> {
			Account account = i.getArgument(0);
			account.setBalance(500.00);
			return account;
		});
		account.setBalance(500.00);
		fundTransferServiceImpl.updateAccount(account);
		assertEquals(account.getBalance(), 500.00);
	}
}
