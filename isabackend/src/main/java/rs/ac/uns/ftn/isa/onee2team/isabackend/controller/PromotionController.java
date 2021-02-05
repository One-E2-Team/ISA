package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
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
}
