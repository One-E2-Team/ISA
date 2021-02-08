package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.LoyaltyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
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
	private IUserService userService;
	
	@Autowired
	public PromotionService(IPromotionRepository promotionRepository, IPharmacyRepository pharmacyRepository, IEmailNotificationService emailNotificationService, ILoyaltyRepository loyaltyRepository, IUserService userService) {
		this.pharmacyRepository = pharmacyRepository;
		this.promotionRepository = promotionRepository;
		this.emailNotificationService = emailNotificationService;
		this.loyaltyRepository = loyaltyRepository;
		this.userService = userService;
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
				emailNotificationService.sendNotificationAsync(patient.getEmail(), subject, mailMessage);
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
		return this.loyaltyRepository.saveAndFlush(l);
	}

	@Override
	public List<Loyalty> getAllLoyalties() {
		return this.loyaltyRepository.findAll();
	}

	@Override
	public List<CategoryType> getPatientType(Integer points) {
		return loyaltyRepository.getPatientType(points);
	}

	@Override
	public Double getDiscount(CategoryType type) {
		return loyaltyRepository.getDiscount(type);
	}

	@Override
	public PharmacyDTO subscribe(Long pharmacyId, Long patientId) {
		Pharmacy pharm = pharmacyRepository.findById(pharmacyId).get();
		Patient patient = (Patient) userService.findById(patientId);
		for (Patient p : pharm.getSubscribedPatients()) 
			if(p.getId().equals(patient.getId()))
				return new PharmacyDTO(pharmacyId, pharm.getName(), pharm.getAddress(), pharm.getDescription());
		pharm.getSubscribedPatients().add(patient);
		pharm = pharmacyRepository.saveAndFlush(pharm);
		return new PharmacyDTO(pharmacyId, pharm.getName(), pharm.getAddress(), pharm.getDescription());
	}

	@Override
	public PharmacyDTO unsubscribe(Long pharmacyId, Long patientId) {
		Pharmacy pharm = pharmacyRepository.findById(pharmacyId).get();
		for (Patient p : pharm.getSubscribedPatients()) 
			if(p.getId().equals(patientId)) {
				pharm.getSubscribedPatients().remove(p);
				pharm = pharmacyRepository.saveAndFlush(pharm);
				return new PharmacyDTO(pharmacyId, pharm.getName(), pharm.getAddress(), pharm.getDescription());
			}
		return new PharmacyDTO(pharmacyId, pharm.getName(), pharm.getAddress(), pharm.getDescription());
	}

	@Override
	public List<PharmacyDTO> getAllSubscriptions(Long patientId) {
		List<PharmacyDTO> ret = new ArrayList<PharmacyDTO>();
		List<Pharmacy> pharmas = pharmacyRepository.findAll();
		for (Pharmacy p : pharmas) {
			for (Patient patient : p.getSubscribedPatients()) {
				if(patient.getId().equals(patientId)) {
					ret.add(new PharmacyDTO(p.getId(), p.getName(), p.getAddress(), p.getDescription()));
					break;
				}
			}
		}
		return ret;
	}
	
}
