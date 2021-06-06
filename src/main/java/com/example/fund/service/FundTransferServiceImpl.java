package com.example.fund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fund.dto.AccountDTO;
import com.example.fund.dto.FundTransfer;
import com.example.fund.entity.Account;
import com.example.fund.repository.AccountRepository;

@Service
public class FundTransferServiceImpl implements FundTransferService {
	private AccountRepository accountRepository;
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	public AccountRepository getAccountRepository() {
		return accountRepository;
	}
	@Override
	public boolean transfer(FundTransfer fundTransfer) {
		
		return false;
	}
	@Override
	public AccountDTO getByUserId(Long userId) {
		return new AccountDTO(accountRepository.findByUserId(userId));
	}
	@Override
	public AccountDTO updateAccount(AccountDTO account) {
		return new AccountDTO(accountRepository.saveAndFlush(account.getAccount()));
	}
	@Override
	public AccountDTO getByAccountId(Long accountId) {
		return new AccountDTO(accountRepository.findById(accountId).get());
	}

}
