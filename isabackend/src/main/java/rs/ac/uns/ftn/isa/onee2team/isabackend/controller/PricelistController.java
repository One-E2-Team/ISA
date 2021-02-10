package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PricelistDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPricelistService;

@RestController
@RequestMapping(value = "api/pricelist")
public class PricelistController {

	private IPricelistService pricelistService;

	@Autowired
	public PricelistController(IPricelistService pricelistService) {
		this.pricelistService = pricelistService;
	}

	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@GetMapping(value = "/pharmacyId")
	public List<PricelistDTO> getValidPricelist(@RequestParam Long pharmacyId) {
		return pricelistService.getValidPricelist(pharmacyId);
	}
	
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@PutMapping(value = "/change")
	public Boolean changePriceList(@RequestBody PricelistDTO pricelist) {
		if(pricelist.getStartDate().compareTo(pricelist.getEndDate()) >= 0)
			return false;
		return pricelistService.changePriceList(pricelist);
	}
	
	@GetMapping(value="/pharmacy/{pid}/medicine/{mid}")
	public PricelistDTO getValidPriceForMedicine(@PathVariable("pid") Long pharmacyId,@PathVariable("mid") Long medicineId) {
		return pricelistService.getValidPriceForMedicine(pharmacyId,medicineId);
	}
}
