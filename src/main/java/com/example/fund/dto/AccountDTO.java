package com.example.fund.dto;

import java.util.List;
import java.util.Objects;

import com.example.fund.entity.Account;
import com.example.fund.entity.Beneficiary;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountDTO {
	private Long id;
	private Long userId;
	private Long accountNumber;
	private Double balance;
	private List<Beneficiary> beneficiaries;
	
	public AccountDTO() {
	}
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.userId = account.getUserId();
		this.accountNumber = account.getAccountNumber();
		this.balance = account.getBalance();
		this.beneficiaries = account.getBeneficiaries();
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
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserId() {
		return userId;
	}
	@JsonIgnore
	public AccountDTO getAccountDTO(Account account) {
		this.id = account.getId();
		this.accountNumber = account.getAccountNumber();
		this.balance = account.getBalance();
		this.userId = account.getUserId();
		this.beneficiaries = account.getBeneficiaries();
		return this;
	}
	@JsonIgnore
	public Account getAccount() {
		return new Account(this.id, this.userId , this.accountNumber,
				this.balance, this.beneficiaries);
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO account = (AccountDTO) o;
        return id == account.id 
        		&& accountNumber == account.accountNumber
        		&& balance == account.balance
        		&& userId == account.userId
        		&& Objects.equals(beneficiaries, account.beneficiaries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, balance, userId, beneficiaries);
    }
}
