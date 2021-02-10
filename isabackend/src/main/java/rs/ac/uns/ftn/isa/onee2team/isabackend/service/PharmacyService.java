package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.CredentialsAndIdDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.DermatologistWithFreeExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ERecipeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ERecipeMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExamStatsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedStatsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyForSearchDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithDoctorsMedicinesAndRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithPrice;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithPriceAndGradeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PresentMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.TimeIntervalDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.ERecipe;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.ERecipeStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pricelist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IERecipeRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineReservationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineWithQuantityRepository;
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
	private IExaminationRepository examRepository;
	private IMedicineWithQuantityRepository mwqRepository;
	private IERecipeRepository eRecipeRepository;
	private IWarehouseRepository wearehouseRepository;
	private IEmailNotificationService emailNotificationService;

	@Autowired
	public PharmacyService(IPharmacyRepository pharmacyRepository, IUserRepository userRepository,
			IMedicineRepository medicineRepository, IRatedPharmacyRepository ratedPharmacyRepository,
			IExaminationService examinationService, IPromotionService promotionService,
			IWarehouseRepository warehouseRepository, IPricelistRepository pricelistRepository, 
			IMedicineReservationRepository reservationRepository, 
			IExaminationRepository examRepository, IMedicineWithQuantityRepository mwqRepository,
			IERecipeRepository eRecipeRepository, IWarehouseRepository wearehouseRepository, 
			IEmailNotificationService emailNotificationService) {
		this.pharmacyRepository = pharmacyRepository;
		this.userRepository = userRepository;
		this.medicineRepository = medicineRepository;
		this.ratedPharmacyRepository = ratedPharmacyRepository;
		this.examinationService = examinationService;
		this.promotionService = promotionService;
		this.warehouseRepository = warehouseRepository;
		this.pricelistRepository = pricelistRepository;
		this.reservationRepository = reservationRepository;
		this.examRepository = examRepository;
		this.mwqRepository = mwqRepository;
		this.eRecipeRepository = eRecipeRepository;
		this.warehouseRepository = warehouseRepository;
		this.emailNotificationService = emailNotificationService;
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
	public List<PharmacyForSearchDTO> findAllIPharmaciesDto() {
		List<PharmacyForSearchDTO> ret_list =  pharmacyRepository.findAllIPharmaciesDto();
		for (PharmacyForSearchDTO dto : ret_list) {
			double rate = pharmacyRepository.getAvgRateForPharmacy(dto.getId());
			dto.setRate(rate);
		}
		return ret_list;
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
						pmdto.getPharmacies().add(new PharmacyWithPrice(w.getPharmacy().getId(), w.getPharmacy().getName(), w.getPharmacy().getAddress(), pls.get(0).getPrice() * (1.0 - discount/100.0)));
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
		for (Examination ex : examinations) {
			dto = new NewRateDTO(ex.getPharmacy().getId(), ex.getPharmacy().getName(), "PHARMACY", -1);
			if (!ret.contains(dto))
				ret.add(dto);
		}

		List<MedicineReservation> reservations = reservationRepository.getDoneReservationsByPatient(patient_id);

		for (MedicineReservation mr : reservations) {
			dto = new NewRateDTO(mr.getPharmacy().getId(), mr.getPharmacy().getName(), "PHARMACY", -1);
			if (!ret.contains(dto))
				ret.add(dto);
		}

		return ret;
	}

	@Override
	public List<ExamStatsDTO> getNumOfExamsByDateInPharmacy(TimeIntervalDTO interval, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if (admin == null)
			return null;
		Map<Date, Integer> ret = new HashMap<Date, Integer>();
		for (Examination exam : examRepository.getAllFinishedByPharmacyInTimeInterval(admin.getPharmacy().getId(),
				interval.getStart(), interval.getEnd())) {
			Date examDate = exam.getDate();
			if (ret.containsKey(examDate)) {
				Integer value = ret.get(examDate);
				value++;
				ret.put(examDate, value);
			} else
				ret.put(examDate, 1);
		}
			return getExamStatsDtoFromMap(ret);
	}

	@Override
	public Double getPharmacyIncomeInTimeInterval(TimeIntervalDTO interval, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if (admin == null)
			return null;
		return null;
	}

	@Override
	public List<MedStatsDTO> getNumOfMedicinesByDateInPharmacy(TimeIntervalDTO interval, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if (admin == null)
			return null;
		Map<MedicineDTO, Integer> ret = new HashMap<MedicineDTO, Integer>();
		for (Examination exam : examRepository.getAllFinishedByPharmacyInTimeInterval(admin.getPharmacy().getId(),
				interval.getStart(), interval.getEnd())) {
			for (Medicine med : exam.getMedicines()) {
				MedicineDTO dto = new MedicineDTO(med.getId(), med.getCode(), med.getName(), med.getContexture(),
						med.getManufacturer());
				if (ret.containsKey(dto)) {
					Integer value = ret.get(dto);
					value--;
					ret.put(dto, value);
				} else {
					ret.put(dto, -1);
				}
			}
		}
		for (MedicineWithQuantity mwq : mwqRepository.medicinesBoughtByPharmacyInTimeInterval(
				admin.getPharmacy().getId(), interval.getStart(), interval.getEnd())) {
			Medicine med = mwq.getMedicine();
			MedicineDTO dto = new MedicineDTO(med.getId(), med.getCode(), med.getName(), med.getContexture(),
					med.getManufacturer());
			if(ret.containsKey(dto)) {
				Integer value = ret.get(dto);
				value += mwq.getQuantity();
				ret.put(dto, value);
			} else {
				ret.put(dto, mwq.getQuantity());
			}
		}
		return getMedStatsDtoFromMap(ret);
	}
	
	private List<MedStatsDTO> getMedStatsDtoFromMap (Map<MedicineDTO, Integer> map){
		List<MedStatsDTO> ret = new ArrayList<MedStatsDTO>();
		for(MedicineDTO med : map.keySet()) {
			MedStatsDTO dto = new MedStatsDTO(med, map.get(med));
			ret.add(dto);
		}
		return ret;
	}
	
	private List<ExamStatsDTO> getExamStatsDtoFromMap (Map<Date, Integer> map){
		List<ExamStatsDTO> ret = new ArrayList<ExamStatsDTO>();
		for(Date date : map.keySet()) {
			ExamStatsDTO dto = new ExamStatsDTO(date, map.get(date));
			ret.add(dto);
		}
		return ret;
	}

	private boolean checkERecipeValidity(String code) {
		if(eRecipeRepository.findByCodeNotRejected(code).size()!=0) return false;
		else return true;
	}
	
	private double getDiscountForPatient(Long patientId) {
		Patient p = (Patient) userRepository.findById(patientId).get();
		List<CategoryType> ct = promotionService.getPatientType(p.getPoints());
		CategoryType type = ct.get(0);
		for (CategoryType categoryType : ct)
			if(type.ordinal() < categoryType.ordinal()) 
				type = categoryType;
		double discount = promotionService.getDiscount(type);
		return discount;
	}
	
	@Override
	public List<PharmacyWithPriceAndGradeDTO> getAllWhereAvailableWithERecipe(ERecipeDTO erdto) {
		if(checkERecipeValidity(erdto.getCode()) == false) return null;
		List<PharmacyWithPriceAndGradeDTO> ret = new ArrayList<PharmacyWithPriceAndGradeDTO>();
		List<Long> pharmacyIds = new ArrayList<Long>();
		double discount = getDiscountForPatient(erdto.getPatientId());
		pharmacyIds.addAll(warehouseRepository.getAllPharmacyIdsWhereMedicineAmountIsAvailable(erdto.getMedicine().get(0).getId(), erdto.getMedicine().get(0).getQuantity()));
		for (ERecipeMedicine ermed : erdto.getMedicine())
			pharmacyIds = pharmacyIds.stream().distinct().filter(warehouseRepository.getAllPharmacyIdsWhereMedicineAmountIsAvailable(ermed.getId(), ermed.getQuantity())::contains).collect(Collectors.toList());
		for (Long pid : pharmacyIds) {
			Pharmacy p = pharmacyRepository.findById(pid).get();
			double price = 0.0;
			for (ERecipeMedicine ermed : erdto.getMedicine()) 
				price += pricelistRepository.getValidPricelistForMedicine(pid, ermed.getId()).get(0).getPrice();
			ret.add(new PharmacyWithPriceAndGradeDTO(pid, p.getName(), p.getAddress(), price * (1.0 - discount/100.0), ratedPharmacyRepository.getAverageRateByPharmacyId(pid)));
		}
		return ret;
	}

	@Override
	public ERecipe buyByERecipe(Long pharmacyId, ERecipeDTO erdto, Long patientId) {
		if(checkERecipeValidity(erdto.getCode()) == false) return null;
		ERecipe e = new ERecipe();
		e.setDate(erdto.getDate());
		e.setCode(erdto.getCode());
		e.setPatient((Patient) userRepository.findById(patientId).get());
		e.setPharmacy(pharmacyRepository.findById(pharmacyId).get());
		e.setStatus(ERecipeStatus.CREATED);
		e.setMedicinesWithQuantity(new ArrayList<MedicineWithQuantity>());
		for (ERecipeMedicine ermed : erdto.getMedicine()) {
			MedicineWithQuantity mwq = new MedicineWithQuantity();
			mwq.setQuantity(ermed.getQuantity());
			mwq.setMedicine(medicineRepository.findById(ermed.getId()).get());
			Warehouse w = warehouseRepository.getByMedicineAndPharmacy(ermed.getId(), pharmacyId);
			w.setReservedAmount(w.getReservedAmount() + ermed.getQuantity());
			warehouseRepository.save(w);
			e.getMedicinesWithQuantity().add(mwq);
		}
		e = eRecipeRepository.save(e);
		emailNotificationService.sendNotificationAsync(e.getPatient().getEmail(), "ERecipe reservation", "Your eRecipe reservation was successfull!");
		return e;
	}
}
