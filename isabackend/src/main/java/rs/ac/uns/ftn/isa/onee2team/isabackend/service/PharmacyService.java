package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.CredentialsAndIdDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.DermatologistWithFreeExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithDoctorsMedicinesAndRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithPrice;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PresentMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pricelist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineReservationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPricelistRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRatedPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWarehouseRepository;

@Service
public class PharmacyService implements IPharmacyService {

	private IPharmacyRepository pharmacyRepository;
	private IUserRepository userRepository;
	private IMedicineRepository medicineRepository;
	private IRatedPharmacyRepository ratedPharmacyRepository;
	private IExaminationService examinationService;
	private IPromotionService promotionService;
	private IWarehouseRepository warehouseRepository;
	private IPricelistRepository pricelistRepository;
	private IMedicineReservationRepository reservationRepository;

	@Autowired
	public PharmacyService(IPharmacyRepository pharmacyRepository, IUserRepository userRepository,
			IMedicineRepository medicineRepository, IRatedPharmacyRepository ratedPharmacyRepository,
			IExaminationService examinationService, IPromotionService promotionService,
			IWarehouseRepository warehouseRepository, IPricelistRepository pricelistRepository, 
			IMedicineReservationRepository reservationRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.userRepository = userRepository;
		this.medicineRepository = medicineRepository;
		this.ratedPharmacyRepository = ratedPharmacyRepository;
		this.examinationService = examinationService;
		this.promotionService = promotionService;
		this.warehouseRepository = warehouseRepository;
		this.pricelistRepository = pricelistRepository;
		this.reservationRepository = reservationRepository;
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
	}
  
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

	@Override
	public List<PresentMedicineDTO> getMedicinesWithPriceForUser(List<PresentMedicineDTO> pmdtos, Long userId) {
		Double discount = 0.0;
		if(userId!=0) {
			Patient p = (Patient) userRepository.findById(userId).get();
			List<CategoryType> ct = promotionService.getPatientType(p.getPoints());
			CategoryType type = ct.get(0);
			for (CategoryType categoryType : ct)
				if(type.ordinal() < categoryType.ordinal()) 
					type = categoryType;
			discount = promotionService.getDiscount(type);
		}
		for (PresentMedicineDTO pmdto : pmdtos) {
			for (Warehouse w : warehouseRepository.findAllByMedicineId(pmdto.getId())) {
				if(w.getAmount() - w.getReservedAmount() > 0) {
					List<Pricelist> pls = pricelistRepository.getValidPricelistForMedicine(w.getPharmacy().getId(), pmdto.getId());
					if(pls.size()!=0) {
						pmdto.getPharmacies().add(new PharmacyWithPrice(w.getPharmacy().getId(), w.getPharmacy().getName(), pls.get(0).getPrice() * (1.0 - discount/100.0)));
					} else System.out.println("Pricelist doesnt exist for item");
				}
			}
		}
		return pmdtos;
	}
	
	@Override
	public List<NewRateDTO> getPharmaciesForRate(Long patient_id) {
		List<Examination> examinations = examinationService.getPatientsFinishedEx(patient_id);
		List<NewRateDTO> ret = new ArrayList<NewRateDTO>();
		NewRateDTO dto;
		for(Examination ex : examinations) {
			dto = new NewRateDTO(ex.getPharmacy().getId(), ex.getPharmacy().getName(), "PHARMACY", -1);
			if(!ret.contains(dto))
				ret.add(dto);
		}
		
		List<MedicineReservation> reservations = reservationRepository.getDoneReservationsByPatient(patient_id);
		
		for(MedicineReservation mr : reservations) {
			dto = new NewRateDTO(mr.getPharmacy().getId(), mr.getPharmacy().getName(), "PHARMACY", -1);
			if(!ret.contains(dto))
				ret.add(dto);
		}
		
		return ret;
	}
}
