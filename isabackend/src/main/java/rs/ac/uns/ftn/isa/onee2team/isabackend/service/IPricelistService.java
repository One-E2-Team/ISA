package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PricelistDTO;

public interface IPricelistService {

	List<PricelistDTO> getValidPricelist(Long pharmacyId);
	Boolean changePriceList(PricelistDTO pricelist);
	PricelistDTO getValidPriceForMedicine(Long pharmacyId, Long medicineId);
}
