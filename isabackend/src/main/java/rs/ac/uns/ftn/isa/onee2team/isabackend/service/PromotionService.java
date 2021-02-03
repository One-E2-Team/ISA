package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPromotionRepository;

@Service
public class PromotionService implements IPromotionService {
	
	private IPromotionRepository promotionRepository;
	private IPharmacyRepository pharmacyRepository;
	private EmailNotificationService emailNotificationService;
	
	@Autowired
	public PromotionService(IPromotionRepository promotionRepository, IPharmacyRepository pharmacyRepository, EmailNotificationService emailNotificationService) {
		this.pharmacyRepository = pharmacyRepository;
		this.promotionRepository = promotionRepository;
		this.emailNotificationService = emailNotificationService;
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
			String subject = "Pharmacy : " + pharmacy.getId() + " new promotion";
			for (Patient patient : pharmacy.getSubscribedPatients()) {
				String mailMessage = "Dear " + patient.getFirstName() + " " + patient.getLastName() + ", pharmacy has added new promotion";
				try {
					emailNotificationService.sendNotificaitionAsync("hgstqyssyzfutkvqpk@miucce.com", subject, mailMessage);
				} catch (Exception e) {}
			}
			return promotionRepository.save(promotion);
		}
		return null;
	}
}
