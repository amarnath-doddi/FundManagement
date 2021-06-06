package com.example.fund.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fund.dto.BeneficiaryDTO;
import com.example.fund.exception.BeneficiaryNotfoundException;
import com.example.fund.exception.DuplicateEntryException;
import com.example.fund.service.BeneficiaryService;

@RestController
@RequestMapping("/api/beneficiary")
public class BeneficiaryController {
	Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);
	private BeneficiaryService beneficiaryService;
	@Autowired
	public void setBeneficiaryService(BeneficiaryService beneficiaryService) {
		this.beneficiaryService = beneficiaryService;
	}
	public BeneficiaryService getBeneficiaryService() {
		return beneficiaryService;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<BeneficiaryDTO>> getBeneficiaries(){
		List<BeneficiaryDTO> beneficies = beneficiaryService.getBeneficiries();
		if(beneficies==null) {
			logger.info("No beneficiaries exist in the system.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(beneficies,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<BeneficiaryDTO> getBeneficiary(@PathVariable Long id) throws BeneficiaryNotfoundException{
		BeneficiaryDTO beneficiary = beneficiaryService.getBeneficiary(id);
		if(beneficiary==null) {
			logger.error("Beneficiary doesn't exist with id :"+id);
			throw new BeneficiaryNotfoundException("Beneficiary doesn't exist with id :"+id);
		}
		return new ResponseEntity<>(beneficiary,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<BeneficiaryDTO> createBeneficiary(@RequestBody BeneficiaryDTO beneficiary) throws DuplicateEntryException{
		BeneficiaryDTO existingBeneficiary= beneficiaryService.getByBeneficiaryAccountNumber(beneficiary.getAccountNumber());
		if(existingBeneficiary!=null) {
			logger.error("Beneficiary already exist!");
			throw new DuplicateEntryException("Beneficiary already exist!");
		}
		BeneficiaryDTO createdBeneficiary = beneficiaryService.createBeneficiary(beneficiary);
		if(createdBeneficiary!=null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(createdBeneficiary,HttpStatus.OK);
	} 
	
	@PutMapping("/")
	public ResponseEntity<BeneficiaryDTO> updateBeneficiary(@RequestBody BeneficiaryDTO beneficiary){
		BeneficiaryDTO udatedBeneficiary = beneficiaryService.updateBeneficiary(beneficiary);
		if(udatedBeneficiary==null || !udatedBeneficiary.equals(beneficiary)) {
			logger.error("Beneficiary updating failed!");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(udatedBeneficiary,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
		 beneficiaryService.deleteBeneficiary(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<List<BeneficiaryDTO>> getBeneficiariesByAccountId(@PathVariable Long id) throws BeneficiaryNotfoundException{
		List<BeneficiaryDTO> beneficiaries = beneficiaryService.getByAccountId(id);
		if(beneficiaries==null) {
			throw new BeneficiaryNotfoundException("Beneficiary doesn't exist for account id :"+id);
		}
		return new ResponseEntity<>(beneficiaries,HttpStatus.OK);
	}
	
}
