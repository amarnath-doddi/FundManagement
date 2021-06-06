package com.example.fund.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fund.dto.BeneficiaryDTO;
import com.example.fund.entity.Beneficiary;
import com.example.fund.repository.BeneficiaryRepository;

@ExtendWith(MockitoExtension.class)
public class BeneficiaryServiceImplTest {
	@Mock
	private BeneficiaryRepository beneficiaryRepository;
	@InjectMocks
	private BeneficiaryServiceImpl beneficiaryServiceImpl;
	
	static BeneficiaryDTO beneficiary;
	
	static BeneficiaryDTO perisistedBeneficiary;
	
	@BeforeAll
	public static void setUp() {
		beneficiary = new BeneficiaryDTO();
		beneficiary.setAccountId(2001L);
		beneficiary.setAccountNumber(32435366L);
		beneficiary.setBalance(0.00);
		beneficiary.setIfscCode("HDFC2324");
		beneficiary.setName("TestBeneficiary");
		
		perisistedBeneficiary = new BeneficiaryDTO();
		perisistedBeneficiary.setId(3001L);
		perisistedBeneficiary.setAccountId(2001L);
		perisistedBeneficiary.setAccountNumber(32435366L);
		perisistedBeneficiary.setBalance(0.00);
		perisistedBeneficiary.setIfscCode("HDFC2324");
		perisistedBeneficiary.setName("TestBeneficiary");
	}
	
	@Test
	@DisplayName("Test Create Beneficiary")
	void testCreateBeneficiary() {
		when(beneficiaryRepository.save(any(Beneficiary.class))).thenAnswer(i -> {
			Beneficiary beneficiary = i.getArgument(0);
			beneficiary.setId(3011L);
			return beneficiary;
		});
		
		BeneficiaryDTO savedBeneficiary = beneficiaryServiceImpl.createBeneficiary(this.beneficiary);
		
		assertTrue(savedBeneficiary.getId()==3011L);
	}
	
	@Test
	@DisplayName("Update Beneficiary Test")
	void testUpdateBeneficiary() {
		when(beneficiaryRepository.saveAndFlush(any(Beneficiary.class))).thenAnswer(i -> {
			Beneficiary beneficiary = i.getArgument(0);
			beneficiary.setId(3001L);
			beneficiary.setBalance(100.00);
			return beneficiary;
		});
		BeneficiaryDTO beneficiary = beneficiaryServiceImpl.updateBeneficiary(this.beneficiary);
		assertEquals(beneficiary.getBalance(), 100.00);
	}
	
}
