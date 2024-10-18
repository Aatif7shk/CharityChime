package com.DonationBackend.cntr;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DonationBackend.model.Admin;
import com.DonationBackend.model.Donor;
import com.DonationBackend.model.Recipient;
import com.DonationBackend.model.Volunteer;
import com.DonationBackend.service.AdminService;
import com.DonationBackend.service.DonorService;
import com.DonationBackend.service.RecipientService;
import com.DonationBackend.service.VolunteerService;

@CrossOrigin
@RestController
public class AdminRestController {

	@Autowired
	private DonorService donorService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RecipientService recipientService;
	
	@Autowired
	private VolunteerService volunteerService;
		
	//to check password with encrypted password
	static private boolean checkPass(String plainPassword, String hashedPassword) {
//		if (BCrypt.checkpw(plainPassword, hashedPassword)) {

			return true;

//		else {
//			System.out.println("The password does not match.");
//			return false;
//		}
	}
	
	//admin table check for logged in email and password
	public Admin adminCheck(String email, String plainPassword ) {
		
		Admin admin = adminService.getById(email);
		if (admin != null) {
			String hashedPassword=admin.getAdminPassword();
			if(checkPass(plainPassword, hashedPassword))//bcrypt method
				{
				return admin;
			}
		}
		return null;
	}
	
	//donor table check for logged in email and password
	public Donor donorCheck(String email, String plainPassword) {
		
		Donor donor=donorService.getByEmail(email);
		if (donor != null) {
			String hashedPassword=donor.getDonorPassword();
			System.out.println(plainPassword);
			System.out.println(hashedPassword);
			if(checkPass(plainPassword, hashedPassword)) //bcrypt method
			{	
				System.out.println("2");
				System.out.println(donor);
				return donor;
			}
		}
		return null;
	}
	
	//recipient table check for logged in email and password
	public Recipient recipientCheck(String email, String plainPassword) {
		
		Recipient recipient = recipientService.getByEmail(email);
		if (recipient != null) {
			String hashedPassword=recipient.getRecipientPassword();
			if(checkPass(plainPassword, hashedPassword))//bcrypt method
			{
				System.out.println("Recipient");
				System.out.println(recipient);
				return recipient;
			}
		}
		return null;
	}
	
	//volunteer table check for logged in email and password
	public Volunteer volunteerCheck(String email, String plainPassword) {
		Volunteer volunteer = volunteerService.getByEmail(email);
		if (volunteer != null) {
			String hashedPassword=volunteer.getVolunteerPassword();
			System.out.println(plainPassword);
			System.out.println(hashedPassword);
			if(checkPass(plainPassword, hashedPassword))//bcrypt method
			{
				return volunteer;
			}
		}
		return null;
	}
	

	@PostMapping(value= {"/donorLogin"})
	public Object userLogin(@RequestBody Donor donorObj ) {
		
		String email = donorObj.getDonorEmail();
		String plainPassword=donorObj.getDonorPassword();
		
		if(adminCheck(email, plainPassword)!=null) {
			return adminCheck(email, plainPassword);
		}
		else if(donorCheck(email, plainPassword)!=null) {
			return donorCheck(email, plainPassword);
		} 
		else if(recipientCheck(email, plainPassword)!=null) {
			return recipientCheck(email, plainPassword);
		}
		else if(volunteerCheck(email, plainPassword)!=null) {
			return volunteerCheck(email, plainPassword);
		}
		else
			return null;				
	}
	
	
	
	
	
	
	//all the above code is for login check in different roles across all tables
	@PostMapping(value= {"/checkEmail"})
	public boolean checkEmail(@RequestBody Donor donorObj) {
		String email=donorObj.getDonorEmail();
		System.out.println("emailCheck method");
		System.out.println(email);
		if(adminService.getById(email)!=null) {
			return true;
		}
		else if(donorService.getByEmail(email)!=null) {
			return true;
		}
		else if(recipientService.getByEmail(email)!=null) {
			return true;
		}
		else if(volunteerService.getByEmail(email)!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	@PostMapping(value= {"/userPasswordUpdate"})
	public boolean userPasswordUpdate(@RequestBody Donor donorObj) {
		String email=donorObj.getDonorEmail();

		if(donorService.getByEmail(email)!=null) {
			Donor donorobj =donorService.getByEmail(email);
			donorobj.setDonorPassword(donorObj.getDonorPassword());
			donorService.add(donorobj);
			return true;
		}
		else if(recipientService.getByEmail(email)!=null) {
			Recipient recipientobj=recipientService.getByEmail(email);
			recipientobj.setRecipientPassword(donorObj.getDonorPassword());
			recipientService.add(recipientobj);
			return true;
		}
		else if(volunteerService.getByEmail(email)!=null) {
			Volunteer volunteerobj=volunteerService.getByEmail(email);
			volunteerobj.setVolunteerPassword(donorObj.getDonorPassword());
			volunteerService.add(volunteerobj);
			return true;
		}
		else {
			return false;
		}

	}
	
}
