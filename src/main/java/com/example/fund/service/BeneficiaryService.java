package com.example.fund.service;

import java.util.List;

import com.example.fund.dto.BeneficiaryDTO;
import com.example.fund.entity.Beneficiary;

public interface BeneficiaryService {
	public BeneficiaryDTO updateBeneficiary(BeneficiaryDTO beneficiary);
	public BeneficiaryDTO getById(Long userId);
	public List<BeneficiaryDTO> getBeneficiries();
	public BeneficiaryDTO createBeneficiary(BeneficiaryDTO beneficiary);
	public void deleteBeneficiary(Long id);
	public BeneficiaryDTO getBeneficiary(Long id);
	public BeneficiaryDTO getByBeneficiaryAccountNumber(Long accountNumber);
	List<BeneficiaryDTO> getByAccountId(Long accountId);
}
