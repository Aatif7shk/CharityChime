package com.DonationBackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.DonationBackend.model.Recipient;

@Repository
public interface RecipientDao extends JpaRepository<Recipient, Integer> {
	
	@Query(value = "select p from Recipient p where p.recipientEmail = :recipientEmail")
	public Recipient selectByEmail(@Param(value = "recipientEmail") String recipientEmail);
	
	@Query(value = "select p from Recipient p where isKYCVerified = false")
	public List<Recipient> selectUnapprovedRecipients();	
	
	@Query(value = "select p from Recipient p where isKYCVerified = true")
	public List<Recipient> selectApprovedRecipients();	
	
	// for landing page
	@Query(value = "select sum(p.rawFoodQuantityRequired) from Recipient p")
	public int selectRawFoodQuantityRequiredSum();
	
	@Query(value = "select sum(p.rawFoodQuantityReceived) from Recipient p")
	public int selectRawFoodQuantityReceivedSum();
	
	@Query(value = "select sum(p.clothesQuantityRequired) from Recipient p")
	public int selectClothesQuantityRequiredSum();
	
	@Query(value = "select sum(p.clothesQuantityReceived) from Recipient p")
	public int selectClothesQuantityReceivedSum();
	
	@Query(value = "select sum(p.stationaryQuantityRequired) from Recipient p")
	public int selectStationaryQuantityRequiredSum();
	
	@Query(value = "select sum(p.stationaryQuantityReceived) from Recipient p")
	public int selectStationaryQuantityReceivedSum();
	
}
