package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPromotionDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;

public interface IPromotionService {
	Promotion save(NewPromotionDTO promotion);
}
