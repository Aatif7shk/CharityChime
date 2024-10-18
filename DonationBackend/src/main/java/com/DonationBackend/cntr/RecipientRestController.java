package com.DonationBackend.cntr;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.DonationBackend.model.DonationDetails;
import com.DonationBackend.model.Recipient;
import com.DonationBackend.service.RecipientService;

@RestController
@CrossOrigin
public class RecipientRestController {

	@Autowired
	private RecipientService recipientService;

	@PostMapping("/recipientRegister")
	public void reciepientPost(@RequestBody Recipient recipient) {
		System.out.println("recipientRegister");
		System.out.println(recipient);
		recipientService.add(recipient);
	}

	//update the kyc status to true
	@PostMapping(value = {"/recipientApproval"})
	public void recipientKYCApproval(@RequestBody Recipient recipient) {

		recipientService.update(recipient);
	}

	//get single recipient based on login
	@GetMapping(value = {"/getRecipient/{recipientId}"})
	public Recipient recipientDetails(@PathVariable int recipientId) {

		return recipientService.getById(recipientId);
	}

	//get all recipients who have isKycVerified as false
	@GetMapping(value = {"/selectUnverifiedRecipients"})
	public List<Recipient> listOfUnverifiedRecipients() {

		return recipientService.getUnapprovedRecipients();
	}

	//get all recipients who have isKycVerified as true
	@GetMapping(value = {"/selectVerifiedRecipients"})
	public List<Recipient> listOfVerifiedRecipients() {

		List<Recipient> list = recipientService.getApprovedRecipients();

		Collections.sort(list);

		return list;
	}

	@PostMapping(value = {"/updateDemands"})
	public void recipientUpdateDemands(@RequestBody Recipient recipient) {

		System.out.println(recipient);

		Recipient updateRecp = recipientService.getById(recipient.getRecipientId());

		updateRecp.setRawFoodQuantityRequired(recipient.getRawFoodQuantityRequired());
		updateRecp.setClothesQuantityRequired(recipient.getClothesQuantityRequired());
		updateRecp.setStationaryQuantityRequired(recipient.getStationaryQuantityRequired());

		System.out.println(updateRecp);
		
		recipientService.update(updateRecp);
	}
	
	

	//updateRecipientRecievedDonationDetails
	@PostMapping(value = {"/updateRecipientRecievedDonationDetails"})
	public void recipientUpdateDemands(@RequestBody DonationDetails donationDetails) {

		System.out.println(donationDetails);

		Recipient updateRecp = donationDetails.getRecipient();
		Recipient oldRecipient = recipientService.getById(updateRecp.getRecipientId());
		int updatedRawFood = updateRecp.getRawFoodQuantityReceived() + oldRecipient.getRawFoodQuantityReceived();
		updateRecp.setRawFoodQuantityReceived(updatedRawFood);
		updateRecp.setClothesQuantityReceived(updateRecp.getClothesQuantityReceived() + oldRecipient.getClothesQuantityReceived());
		updateRecp.setStationaryQuantityReceived(updateRecp.getStationaryQuantityReceived() + oldRecipient.getStationaryQuantityReceived());

		System.out.println(updateRecp);

		recipientService.update(updateRecp);
	}

	//get all recipients 
	@GetMapping(value = {"/selectAllRecipients"})
	public List<Recipient> listOfAllRecipients() {

		return recipientService.getAll();
	}

	
	//get rawFood received/required percentage 
	@GetMapping(value = {"/getRawFoodPercentage"})
	public int getRawFoodPercentage() {
		try {
			return (100 * recipientService.getRawFoodQuantityReceivedSum()) / recipientService.getRawFoodQuantityRequiredSum();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	//get clothes received/required percentage
	@GetMapping(value = {"/getClothesPercentage"})
	public int getClothesPercentage() {
		try {
			return (100 * recipientService.getClothesQuantityReceivedSum()) / recipientService.getClothesQuantityRequiredSum();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	//get stationary received/required percentage
	@GetMapping(value = {"/getStationaryPercentage"})
	public int getStationaryPercentage() {
		try {
			return (100 * recipientService.getStationaryQuantityReceivedSum()) / recipientService.getStationaryQuantityRequiredSum();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
}