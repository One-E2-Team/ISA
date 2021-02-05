package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.CredentialsAndIdDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.DermatologistWithFreeExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithDoctorsMedicinesAndRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRatedPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class PharmacyService implements IPharmacyService {

	private IPharmacyRepository pharmacyRepository;
	private IUserRepository userRepository;
	private IMedicineRepository medicineRepository;
	private IRatedPharmacyRepository ratedPharmacyRepository;
	private IExaminationService examinationService;

	@Autowired
	public PharmacyService(IPharmacyRepository pharmacyRepository, IUserRepository userRepository,
			IMedicineRepository medicineRepository, IRatedPharmacyRepository ratedPharmacyRepository,
			IExaminationService examinationService) {
		this.pharmacyRepository = pharmacyRepository;
		this.userRepository = userRepository;
		this.medicineRepository = medicineRepository;
		this.ratedPharmacyRepository = ratedPharmacyRepository;
		this.examinationService = examinationService;
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
	public List<Pharmacy> findAll() {
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
  
  @Override
	public PharmacyWithDoctorsMedicinesAndRateDTO getPharmacyById(Long id) {
		Pharmacy pharmacy = getById(id);
		if (pharmacy == null)
			return null;
		PharmacyWithDoctorsMedicinesAndRateDTO dto = new PharmacyWithDoctorsMedicinesAndRateDTO();
		dto.setName(pharmacy.getName());
		dto.setAddress(pharmacy.getAddress());
		dto.setPharmacists(getCredentialsFromHealthWorkers(id, true));
		dto.setDermatologists(getCredentialsFromHealthWorkers(id, false));
		dto.setMedicines(medicineRepository.findMedicineByPharmacyid(id));
		dto.setRate(ratedPharmacyRepository.getAverageRateByPharmacyId(id));
		return dto;
	}

	private List<DermatologistWithFreeExaminationsDTO> getCredentialsFromHealthWorkers(Long pharmacyId,
			Boolean isPharmacist) {
		List<DermatologistWithFreeExaminationsDTO> ret = new ArrayList<DermatologistWithFreeExaminationsDTO>();
		List<? extends HealthWorker> workers = null;
		if (isPharmacist)
			workers = userRepository.getAllPharmacistsByPharmacyId(pharmacyId);
		else
			workers = userRepository.getAllDermatologistsByPharmacyId(pharmacyId);
		for (HealthWorker worker : workers) {
			DermatologistWithFreeExaminationsDTO dto = new DermatologistWithFreeExaminationsDTO();
			dto.setCredentials(new CredentialsAndIdDTO(worker.getId(), worker.getFirstName(), worker.getLastName()));
			dto.setFreeExaminations(
					examinationService.getFreeExaminationsByHealthWorkerIdAndPharmacyId(worker.getId(), pharmacyId));
			ret.add(dto);
		}
		return ret;
	}
}
