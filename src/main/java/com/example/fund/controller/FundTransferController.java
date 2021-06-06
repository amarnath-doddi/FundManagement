package com.example.fund.controller;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fund.dto.AccountDTO;
import com.example.fund.dto.BeneficiaryDTO;
import com.example.fund.dto.FundTransfer;
import com.example.fund.dto.UserDTO;
import com.example.fund.exception.InsufficientFundException;
import com.example.fund.service.BeneficiaryService;
import com.example.fund.service.FundTransferService;
import com.example.fund.service.UserRegistrationService;

@RestController
@RequestMapping("/api/fund")
public class FundTransferController {
	Logger logger = LoggerFactory.getLogger(FundTransferController.class);
	private FundTransferService fundTransferService;
	private BeneficiaryService beneficiaryService;
	private UserRegistrationService userService;
	@Autowired
	public void setFundTransferService(FundTransferService fundTransferService) {
		this.fundTransferService = fundTransferService;
	}
	@Autowired
	public void setBeneficiaryService(BeneficiaryService beneficiaryService) {
		this.beneficiaryService = beneficiaryService;
	}
	@Autowired
	public void setUserService(UserRegistrationService userService) {
		this.userService = userService;
	}
	public FundTransferService getFundTransferService() {
		return fundTransferService;
	}
	public BeneficiaryService getBeneficiaryService() {
		return beneficiaryService;
	}
	public UserRegistrationService getUserService() {
		return userService;
	}
	@PutMapping("/")
	public ResponseEntity<UserDTO> transfer(@RequestBody FundTransfer fundTransfer){
		
		BiFunction<Double,Double,Double> addAmount = (a,b) -> a + b;
		BiFunction<Double,Double,Double> subAmount = (a,b) -> a -b;
		AccountDTO account = fundTransferService.getByAccountId(beneficiaryService.getBeneficiary(fundTransfer.getBeneficaryId()).getAccountId());
		//insufficient funds
		if(fundTransfer.getAmount()>account.getBalance()) {
			logger.error("Insufficient fund :"+fundTransfer.getAmount()+" to transfer. Available balance only :"+account.getBalance());
			throw new InsufficientFundException("Insufficient fund :"+fundTransfer.getAmount()+" to transfer. Available balance only :"+account.getBalance());
		}
		account.setBalance(subAmount.apply(account.getBalance(), fundTransfer.getAmount()));
		fundTransferService.updateAccount(account);
		BeneficiaryDTO beneficiary = beneficiaryService.getBeneficiary(fundTransfer.getBeneficaryId());
		beneficiary.setBalance(addAmount.apply(beneficiary.getBalance(), fundTransfer.getAmount()));
		beneficiaryService.updateBeneficiary(beneficiary);
		UserDTO user = userService.getUser(account.getUserId());
		logger.info("Amount transfered successfully.");
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping("/account/user/{userId}")
	public ResponseEntity<AccountDTO> getAccountByUserId(@PathVariable Long userId){
		AccountDTO account = fundTransferService.getByUserId(userId);
		return new ResponseEntity<>(account,HttpStatus.OK);
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId){
		AccountDTO account = fundTransferService.getByAccountId(accountId);
		return new ResponseEntity<>(account,HttpStatus.OK);
	}
}
