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
		List<Long> medicines = medicineRepository.findMedicineIdsByPharmacyid(pharmacyId);
		List<Long> medicinesInPricelists = new ArrayList<Long>();
		for (Pricelist pricelist : pricelistRepository.getValidPricelist(pharmacyId)) {
			medicinesInPricelists.add(pricelist.getMedicine().getId());
			PricelistDTO dto = new PricelistDTO(pricelist.getId(), pricelist.getMedicine().getId(),
					pricelist.getPrice(), pricelist.getStartDate(), pricelist.getEndDate(), pharmacyId);
			ret.add(dto);
		}
		for (Long id : medicines) {
			if(!medicinesInPricelists.contains(id)) {
				PricelistDTO dto = new PricelistDTO();
				dto.setMedicineId(id);
				dto.setPrice(-1.0);
				dto.setPharmacyId(pharmacyId);
				ret.add(dto);
			}
		}
		return ret;
	}

	@Override
	public Boolean changePriceList(PricelistDTO pricelist) {
		if(isValidNewPricelist(pricelist)) {
			Pricelist newPricelist = new Pricelist();
			newPricelist.setMedicine(medicineRepository.findById(pricelist.getMedicineId()).orElse(null));
			newPricelist.setPharmacy(pharmacyRepository.findById(pricelist.getPharmacyId()).orElse(null));
			newPricelist.setEndDate(pricelist.getEndDate());
			newPricelist.setPrice(pricelist.getPrice());
			newPricelist.setStartDate(pricelist.getStartDate());
			pricelistRepository.save(newPricelist);
			return true;
		}
		return false;
	}

	@Override
	public PricelistDTO getValidPriceForMedicine(Long pharmacyId, Long medicineId) {
		for(PricelistDTO pl : getValidPricelist(pharmacyId)) {
			if(pl.getMedicineId().equals(medicineId))
				return pl;
		}
		return null;
	}
	
	private Boolean isValidNewPricelist(PricelistDTO pricelist) {
		for(Pricelist p : pricelistRepository.getAllPricelistsForPharmacyAndMedicine(pricelist.getPharmacyId(), pricelist.getMedicineId())) {
			if(pricelist.getStartDate().after(p.getStartDate()) && pricelist.getEndDate().before(p.getEndDate()))
				return false;
			if(p.getStartDate().after(pricelist.getStartDate()) && p.getEndDate().before(pricelist.getEndDate()))
				return false;
			if(p.getStartDate().equals(pricelist.getStartDate()) || p.getEndDate().equals(pricelist.getEndDate()))
				return false;
			if(p.getStartDate().after(pricelist.getStartDate()) && p.getStartDate().before(pricelist.getEndDate()))
				return false;
			if(pricelist.getStartDate().after(p.getStartDate()) && pricelist.getStartDate().before(p.getEndDate()))
				return false;
		}
		return true;
	}

}
