package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.LoyaltyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Loyalty;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.ILoyaltyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPromotionRepository;

@Service
public class PromotionService implements IPromotionService {
	
	private IPromotionRepository promotionRepository;
	private IPharmacyRepository pharmacyRepository;
	private IEmailNotificationService emailNotificationService;
	private ILoyaltyRepository loyaltyRepository;
	
	@Autowired
	public PromotionService(IPromotionRepository promotionRepository, IPharmacyRepository pharmacyRepository, IEmailNotificationService emailNotificationService, ILoyaltyRepository loyaltyRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.promotionRepository = promotionRepository;
		this.emailNotificationService = emailNotificationService;
		this.loyaltyRepository = loyaltyRepository;
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
				emailNotificationService.sendNotificaitionAsync("hgstqyssyzfutkvqpk@miucce.com", subject, mailMessage);
			}
			return promotionRepository.save(promotion);
		}
		return null;
	}

	@Override
	public Loyalty save(LoyaltyDTO ldto) {
		Loyalty l = loyaltyRepository.findAllByType(ldto.getType()).get(0);
		l.setDiscount(ldto.getDiscount());
		l.setExaminationPoints(ldto.getExaminationPoints());
		l.setMinPoints(ldto.getMinPoints());
		return this.loyaltyRepository.save(l);
	}

	@Override
	public List<Loyalty> getAllLoyalties() {
		return this.loyaltyRepository.findAll();
	}
	
	
}
