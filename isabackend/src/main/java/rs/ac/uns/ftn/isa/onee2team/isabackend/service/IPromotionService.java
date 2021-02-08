package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;


import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.LoyaltyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Loyalty;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;

public interface IPromotionService {
	Promotion save(NewPromotionDTO promotion);
	Loyalty save(LoyaltyDTO ldto);
	List<Loyalty> getAllLoyalties();
	List<CategoryType> getPatientType(Integer points);
	Double getDiscount(CategoryType type);
	PharmacyDTO subscribe(Long pharmacyId, Long patientId);
	PharmacyDTO unsubscribe(Long pharmacyId, Long patientId);
	List<PharmacyDTO> getAllSubscriptions(Long patientId);
}
