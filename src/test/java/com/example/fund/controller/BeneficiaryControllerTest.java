package com.example.fund.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fund.dto.BeneficiaryDTO;
import com.example.fund.entity.Beneficiary;
import com.example.fund.exception.BeneficiaryNotfoundException;
import com.example.fund.service.BeneficiaryServiceImpl;

@ExtendWith(MockitoExtension.class)
class BeneficiaryControllerTest {
	@Mock
	private BeneficiaryServiceImpl beneficiaryServiceImpl;
	@InjectMocks
	private BeneficiaryController beneficiaryController;
	
	private static List<BeneficiaryDTO> beneficiarys;
	private static BeneficiaryDTO beneficiary;
	private static BeneficiaryDTO beneficiary1;
	@BeforeAll
	public static void setUp() {
		beneficiary = new BeneficiaryDTO();
		beneficiary.setId(3001l);
		beneficiary.setAccountId(2001L);
		beneficiary.setAccountNumber(32435366L);
		beneficiary.setBalance(0.00);
		beneficiary.setIfscCode("HDFC2324");
		beneficiary.setName("TestBeneficiary");
		
		beneficiary1 = new BeneficiaryDTO();
		beneficiary.setId(3002l);
		beneficiary1.setAccountId(2002L);
		beneficiary1.setAccountNumber(99000366L);
		beneficiary1.setBalance(100.00);
		beneficiary1.setIfscCode("ICICI5624");
		beneficiary1.setName("TestBeneficiary1");
		
		beneficiarys = new ArrayList<>();
		beneficiarys.add(beneficiary);
		beneficiarys.add(beneficiary1);
		
		
	}
	
	@Test
	@DisplayName("Get all Beneficiaries test")
	void testGetAllBeneficiaries() {
		when(beneficiaryServiceImpl.getBeneficiries()).thenReturn(beneficiarys);
		
		List<BeneficiaryDTO> persistedBeneficiarys = beneficiaryController.getBeneficiaries().getBody();
		
		verify(beneficiaryServiceImpl).getBeneficiries();
		
		assertEquals(beneficiarys, persistedBeneficiarys);
	}
	
	@Test
	@DisplayName("Get Beneficiary by id test")
	void testGetBeneficiaryById() {
		when(beneficiaryServiceImpl.getBeneficiary(any(Long.class))).thenAnswer(i -> {
			Long beneficiaryId = i.getArgument(0);
			beneficiary.setId(beneficiaryId);
			return beneficiary;
		});
		
		BeneficiaryDTO persistedBeneficiary = beneficiaryController.getBeneficiary(3001L).getBody();
		
		//verify(beneficiaryServiceImpl).getBeneficiary(3001L);
		
		assertEquals(beneficiary, persistedBeneficiary);
	}
	
	@Test
	@DisplayName("Get User by id: Negative Scenario")
	void testGetUserByIdNotFound() {
		//context
		when(beneficiaryServiceImpl.getBeneficiary(5L)).thenThrow(BeneficiaryNotfoundException.class);
		
		//event
		//outcome
		assertThrows(BeneficiaryNotfoundException.class, ()->beneficiaryServiceImpl.getBeneficiary(5L));
	}
	
	@Test
	@DisplayName("Update user test")
	void testUpdateUser() {
		when(beneficiaryServiceImpl.updateBeneficiary(any(BeneficiaryDTO.class))).thenAnswer(i -> {
			BeneficiaryDTO beneficiary = i.getArgument(0);
			beneficiary.setId(3001L);
			beneficiary.setName("Test");
			return beneficiary;
		});
		
		BeneficiaryDTO persistedBeneficiary = beneficiaryController.updateBeneficiary(beneficiary).getBody();
		
		assertEquals("Test", persistedBeneficiary.getName());
	}
	
	
	@Test
	@DisplayName("Save Beneficiary test")
	void testSaveUser() {
		when(beneficiaryServiceImpl.createBeneficiary(any(BeneficiaryDTO.class))).thenAnswer(i -> {
			BeneficiaryDTO beneficiary = i.getArgument(0);
			beneficiary.setId(30011L);
			return beneficiary;
		});
		
		BeneficiaryDTO persistedBeneficiary = beneficiaryServiceImpl.createBeneficiary(beneficiary);
		
		assertEquals(beneficiary, persistedBeneficiary);
	}
}
