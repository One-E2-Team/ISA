package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PricelistDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pricelist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPricelistRepository;

@Service
public class PricelistService implements IPricelistService {

	private IPricelistRepository pricelistRepository;
	private IMedicineRepository medicineRepository;
	private IPharmacyRepository pharmacyRepository;

	@Autowired
	public PricelistService(IPricelistRepository pricelistRepository, IMedicineRepository medicineRepository, IPharmacyRepository pharmacyRepository) {
		this.pricelistRepository = pricelistRepository;
		this.medicineRepository = medicineRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public List<PricelistDTO> getValidPricelist(Long pharmacyId) {
		List<PricelistDTO> ret = new ArrayList<PricelistDTO>();
		for (Pricelist pricelist : pricelistRepository.getValidPricelist(pharmacyId)) {
			PricelistDTO dto = new PricelistDTO(pricelist.getId(), pricelist.getMedicine().getId(),
					pricelist.getPrice(), pricelist.getStartDate(), pricelist.getEndDate(), pharmacyId);
			ret.add(dto);
		}
		return ret;
	}

	@Override
	public Boolean changePriceList(PricelistDTO pricelist) {
		Pricelist newPricelist = new Pricelist();
		System.out.println("metoda sevis");
		newPricelist.setMedicine(medicineRepository.findById(pricelist.getMedicineId()).orElse(null));
		newPricelist.setPharmacy(pharmacyRepository.findById(pricelist.getPharmacyId()).orElse(null));
		newPricelist.setEndDate(pricelist.getEndDate());
		newPricelist.setPrice(pricelist.getPrice());
		newPricelist.setStartDate(pricelist.getStartDate());
		pricelistRepository.save(newPricelist);
		return true;
	}

}
