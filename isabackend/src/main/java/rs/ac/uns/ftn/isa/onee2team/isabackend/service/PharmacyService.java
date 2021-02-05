package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;

@Service
public class PharmacyService implements IPharmacyService{
	
	private IPharmacyRepository pharmacyRepository;
	
	@Autowired
	public PharmacyService(IPharmacyRepository pharmacyRepository) {
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public Pharmacy getById(Long id) {
		return pharmacyRepository.findById(id).orElse(null);
	}

	@Override
	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyRepository.save(pharmacy);
	}
	
	@Override
	public List<Pharmacy> findAll(){
		return pharmacyRepository.findAll();
	}

	@Override
	public List<PharmacyDTO> findAllIPharmaciesDto() {
		return pharmacyRepository.findAllIPharmaciesDto();
	}

	@Override
	public Pharmacy registerPharmacy(NewPharmacyDTO phdto) {
		Pharmacy p = new Pharmacy();
		p.setName(phdto.getName());
		p.setSubscribedPatients(new HashSet<Patient>());
		p.setAddress(phdto.getAddress());
		p.setDescription(phdto.getDescription());
		return save(p);
	}
}
