package com.example.fund.dto;

import java.util.Objects;
import java.util.Optional;

import com.example.fund.entity.Beneficiary;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BeneficiaryDTO {
	private Long id;
	private Long accountNumber;
	private String name;
	private String ifscCode;
	private Double balance;
	private Long accountId;
	public BeneficiaryDTO() {
	}
	public BeneficiaryDTO(Optional<Beneficiary> beneficiaryOptional) {
		if(beneficiaryOptional.isPresent()) {
			Beneficiary beneficiary = beneficiaryOptional.get();
			this.id = beneficiary.getId();
			this.accountId = beneficiary.getAccountId();
			this.accountNumber = beneficiary.getAccountNumber();
			this.balance = beneficiary.getBalance();
			this.ifscCode = beneficiary.getIfscCode();
			this.name = beneficiary.getName();
		}
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public BeneficiaryDTO getBeneficiaryDTO(Beneficiary beneficiary) {
		this.id = beneficiary.getId();
		this.accountId = beneficiary.getAccountId();
		this.accountNumber = beneficiary.getAccountNumber();
		this.balance = beneficiary.getBalance();
		this.ifscCode = beneficiary.getIfscCode();
		this.name = beneficiary.getName();
		return this;
	}
	
	@JsonIgnore
	public Beneficiary getBeneficiary() {
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setId(id);
		beneficiary.setAccountId(accountId);
		beneficiary.setAccountNumber(accountNumber);
		beneficiary.setBalance(balance);
		beneficiary.setIfscCode(ifscCode);
		beneficiary.setName(name);
		return beneficiary;
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null||getClass()!=obj.getClass()) { return false;}
		
		 BeneficiaryDTO beneficiary = (BeneficiaryDTO) obj;
	        return id == beneficiary.id 
	        		&& balance == beneficiary.balance
	        		&& accountId == beneficiary.accountId
	        		&& accountNumber == beneficiary.accountNumber
	        		&& Objects.equals(ifscCode, beneficiary.ifscCode)
	        		&& Objects.equals(name, beneficiary.name);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id, balance, accountId, accountNumber, ifscCode, name);
	    }
}
