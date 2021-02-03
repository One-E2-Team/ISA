package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPromotionRepository;

@Service
public class PromotionService implements IPromotionService {
	
	private IPromotionRepository promotionRepository;
	private IPharmacyRepository pharmacyRepository;
	
	@Autowired
	public PromotionService(IPromotionRepository promotionRepository, IPharmacyRepository pharmacyRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.promotionRepository = promotionRepository;
	}

	@Override
	public Promotion save(NewPromotionDTO newPromotion) {
		Promotion promotion = new Promotion();
		promotion.setDescription(newPromotion.getDescription());
		promotion.setStartDate(newPromotion.getStartDate());
		promotion.setEndDate(newPromotion.getEndDate());
		Pharmacy pharmacy = pharmacyRepository.findById(newPromotion.getPharmacyId()).orElse(null);
		if(pharmacy != null) {
			promotion.setPharmacy(pharmacy);
			return promotionRepository.save(promotion);
		}
		return null;
	}
}
