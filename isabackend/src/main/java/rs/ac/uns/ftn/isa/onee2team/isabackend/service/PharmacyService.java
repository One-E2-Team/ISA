package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.CredentialsAndIdDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.DermatologistWithFreeExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExamStatsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedStatsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithDoctorsMedicinesAndRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.TimeIntervalDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineReservationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineWithQuantityRepository;
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
	private IMedicineReservationRepository reservationRepository;
	private IExaminationRepository examRepository;
	private IMedicineWithQuantityRepository mwqRepository;

	@Autowired
	public PharmacyService(IPharmacyRepository pharmacyRepository, IUserRepository userRepository,
			IMedicineRepository medicineRepository, IRatedPharmacyRepository ratedPharmacyRepository,
			IExaminationService examinationService, IMedicineReservationRepository reservationRepository,
			IExaminationRepository examRepository, IMedicineWithQuantityRepository mwqRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.userRepository = userRepository;
		this.medicineRepository = medicineRepository;
		this.ratedPharmacyRepository = ratedPharmacyRepository;
		this.examinationService = examinationService;
		this.reservationRepository = reservationRepository;
		this.examRepository = examRepository;
		this.mwqRepository = mwqRepository;
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
}
