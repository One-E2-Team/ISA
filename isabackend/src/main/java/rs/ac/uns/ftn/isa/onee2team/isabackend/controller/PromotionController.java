package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.LoyaltyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Loyalty;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPromotionService;

@RestController
@RequestMapping(value = "api/promotions")
public class PromotionController {

	private IPromotionService promotionService;

	@Autowired
	public PromotionController(IPromotionService promotionService) {
		this.promotionService = promotionService;
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public Promotion save(@RequestBody NewPromotionDTO newPromotion) {
		return promotionService.save(newPromotion);
	}
	
	@PostMapping(value = "/loyalty")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public Loyalty save(@RequestBody LoyaltyDTO ldto) {
		return promotionService.save(ldto);
	}
	
	@GetMapping(value = "/loyalty/all")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public List<Loyalty> getAllLoyalties() {
		return promotionService.getAllLoyalties();
	}
	
	@GetMapping(value = "/subscribe/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public PharmacyDTO subscribe(@PathVariable("id") Long pharmacyId, Authentication auth) {
		return promotionService.subscribe(pharmacyId, ((User) auth.getPrincipal()).getId());
	}
	
	@GetMapping(value = "/unsubscribe/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public PharmacyDTO unsubscribe(@PathVariable("id") Long pharmacyId, Authentication auth) {
		return promotionService.unsubscribe(pharmacyId, ((User) auth.getPrincipal()).getId());
	}
	
	@GetMapping(value = "/subscriptions")
	@PreAuthorize("hasRole('PATIENT')")
	public List<PharmacyDTO> subscriptions(Authentication auth) {
		return promotionService.getAllSubscriptions(((User) auth.getPrincipal()).getId());
	}
}
