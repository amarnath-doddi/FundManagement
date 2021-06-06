package com.example.fund.service;

import com.example.fund.dto.AccountDTO;
import com.example.fund.dto.FundTransfer;

public interface FundTransferService {
	public boolean transfer(FundTransfer fundTransfer);
	public AccountDTO getByUserId(Long userId);
	public AccountDTO updateAccount(AccountDTO account);
	public AccountDTO getByAccountId(Long accountId);
}
