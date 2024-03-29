package com.example.fund.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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
import com.example.fund.dto.BeneficiaryDTO;
import com.example.fund.dto.FundTransfer;
import com.example.fund.dto.UserDTO;
import com.example.fund.exception.InsufficientFundException;
import com.example.fund.service.BeneficiaryServiceImpl;
import com.example.fund.service.FundTransferServiceImpl;
import com.example.fund.service.UserRegistrationServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class TransferFundControllerTest {
	@Mock
	private FundTransferServiceImpl fundTransferServiceImpl;
	@Mock
	private BeneficiaryServiceImpl beneficiaryService;
	@Mock
	private UserRegistrationServiceImpl userRegistrationServiceImpl;
	@InjectMocks
	private FundTransferController fundTransferController;
	private static FundTransfer fundTransfer;
	private static BeneficiaryDTO beneficiary;
	private static AccountDTO account;
	private static UserDTO user;
	@BeforeAll
	public static void setUp() {
		fundTransfer = new FundTransfer();
		fundTransfer.setAmount(500.00);
		fundTransfer.setBeneficaryId(3001L);
		
		beneficiary = new BeneficiaryDTO();
		beneficiary.setId(3001l);
		beneficiary.setAccountId(2001L);
		beneficiary.setAccountNumber(32435366L);
		beneficiary.setBalance(0.00);
		beneficiary.setIfscCode("HDFC2324");
		beneficiary.setName("TestBeneficiary");
		
		account = new AccountDTO();
		account.setId(2001L);
		account.setUserId(1000L);
		account.setBalance(23432.00);
		account.setAccountNumber(676766689L);
		
		user = new UserDTO();
		user.setId(1000L);
		user.setLoginId("amardoddi");
		user.setPassword("amardoddi");
		user.setFirstName("Amarnath");
		user.setLastName("Doddi");
		user.setEmail("amarnath@gmail.com");
		user.setPhone("9123456790");
		user.setLastUpdated(LocalDate.now());
		
	}
	
	@Test
	@DisplayName("Test Fund Transfer")
	@Order(1)
	void testTransfer() {
		when(beneficiaryService.getBeneficiary(fundTransfer.getBeneficaryId())).thenReturn(beneficiary);
		when(fundTransferServiceImpl.getByAccountId(beneficiary.getAccountId())).thenReturn(account);
		when(userRegistrationServiceImpl.getUser(any(Long.class))).thenReturn(user);
		//when(fundTransferServiceImpl.transfer(any(FundTransfer.class))).thenReturn(true);

		UserDTO user = fundTransferController.transfer(fundTransfer).getBody();
		assertNotNull(user);
	}
	@Test
	@DisplayName("Negitive senario:Test Insufficent Funds to Transfer")
	@Order(2)
	void testInsufficeFundsToTransfer() {
		account.setBalance(500.00);
		fundTransfer.setAmount(1000.00);
		when(beneficiaryService.getBeneficiary(fundTransfer.getBeneficaryId())).thenReturn(beneficiary);
		when(fundTransferServiceImpl.getByAccountId(beneficiary.getAccountId())).thenReturn(account);
		//when(userRegistrationServiceImpl.getUser(any(Long.class))).thenReturn(user);
		//when(fundTransferServiceImpl.transfer(any(FundTransfer.class))).thenThrow(InsufficientFundException.class);

		assertThrows(InsufficientFundException.class, ()->fundTransferController.transfer(fundTransfer));
	}
}
