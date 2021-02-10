package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class ExaminationService implements IExaminationService {

	private IExaminationRepository examinationRepository;
	private IUserRepository userRepository;
	private IPharmacyRepository pharmacyRepository;
	private IEmailNotificationService emailService;


	@Autowired
	public ExaminationService(IExaminationRepository examinationRepository, IUserRepository userRepository, IPharmacyRepository pharmacyRepository,IEmailNotificationService emailService) {
		this.examinationRepository = examinationRepository;
		this.userRepository = userRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.emailService = emailService;
	}

	@Override
	public List<ExaminationDTO> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId) {
		List<ExaminationDTO> ret = new ArrayList<ExaminationDTO>();
		for (Examination examination : examinationRepository
				.getFreeExaminationsByHealthWorkerIdAndPharmacyId(healthWorkerId, pharmacyId)) {
			ret.add(new ExaminationDTO(examination.getId(), healthWorkerId, pharmacyId, examination.getDate(),
					examination.getStartTime(), examination.getEndTime()));
		}
		return ret;
	}

	@Override

	public List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status){

		List<ExaminationDTO> ret = new ArrayList<ExaminationDTO>();
		HealthWorker worker = (HealthWorker) userRepository.findById(healthWorkerId).orElse(null);
		for (Examination examination : examinationRepository.getExaminationsByHealthWorkerIdInTimeInterval(worker,timeStart,timeEnd,status)) {
			ret.add(new ExaminationDTO(examination.getId(),healthWorkerId,examination.getPharmacy().getId(),examination.getPharmacy().getName(),examination.getDate(),examination.getStartTime(),examination.getEndTime()));
		}
		return ret;
	}

	@Override
	public List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId, Date timeStart,
			Date timeEnd, ExaminationStatus status, Long pharmacyId) {
		List<ExaminationDTO> ret = new ArrayList<ExaminationDTO>();
		HealthWorker worker = (HealthWorker) userRepository.findById(healthWorkerId).orElse(null);
		Pharmacy pharmacy = (Pharmacy) pharmacyRepository.findById(pharmacyId).orElse(null);
		for (Examination examination : examinationRepository.getExaminationsByHealthWorkerIdInTimeInterval(worker,timeStart,timeEnd,status,pharmacy)) {
			ret.add(new ExaminationDTO(examination.getId(),healthWorkerId,examination.getPharmacy().getId(),examination.getPharmacy().getName(),examination.getDate(),examination.getStartTime(),examination.getEndTime()));
		}
		return ret;
	}

	public List<ScheduledExaminationDTO> getFreeExaminationsAtDermatologist() {
		List<Examination> examinations = examinationRepository.getFreeExaminationsAtDermatologist();
		List<ScheduledExaminationDTO> ret_list = new ArrayList<ScheduledExaminationDTO>();
		for(Examination ex : examinations) {
			double rate = examinationRepository.getAvgRateForHealthWorker(ex.getHealthWokrer().getId());
			ret_list.add(new ScheduledExaminationDTO(
						ex.getId(), ex.getStartTime().toString(), ex.getHealthWokrer().getId(), 
						ex.getHealthWokrer().getFirstName(), ex.getHealthWokrer().getLastName(), 
						ex.getHealthWokrer().getUserType().toString(),rate,ex.getPrice()
					));
		}
		
		return ret_list;
	}

	@Override
	public boolean scheduleAtDermatologist(Long patientId, Long examinationId) {
		if(userRepository.getPatientsPenalties(patientId) >= 3)
			return false;
		Examination examination =  examinationRepository.findById(examinationId).orElse(null);
		examination.setPatient((Patient)(userRepository.findById(patientId).orElse(null)));
		examination.setStatus(ExaminationStatus.SCHEDULED);
		examinationRepository.save(examination);
		User user = userRepository.findById(patientId).orElse(null);
		emailService.sendNotificationAsync(user.getEmail(), "Scheduled appointment", 
				"You have successfully scheduled an appointment at dermatologist.");
		return true;
	}

	@Override
	public void cancelAppointment(Long examinationId) {
		Examination ex = examinationRepository.findById(examinationId).orElse(null);
		ex.setStatus(ExaminationStatus.CANCELED);
		examinationRepository.save(ex);
	}

	@Override
	public List<ScheduledExaminationDTO> getPatientsExaminations(Long patientId) {
		List<Examination> examinations =  examinationRepository.getScheduledAppointments(patientId);
		List<ScheduledExaminationDTO> ret_list = new ArrayList<ScheduledExaminationDTO>();
		for(Examination ex : examinations) {
			double rate = examinationRepository.getAvgRateForHealthWorker(ex.getHealthWokrer().getId());
			ret_list.add(new ScheduledExaminationDTO(
					ex.getId(), ex.getStartTime().toString(), ex.getHealthWokrer().getId(),
					ex.getHealthWokrer().getFirstName(), ex.getHealthWokrer().getLastName(),
					ex.getHealthWokrer().getUserType().toString(),rate, ex.getPrice())
					);}
		return ret_list;
		}

	@Override

	public Patient getPatientFromExamination(Long id) {
		return (examinationRepository.findById(id).orElse(null)).getPatient();
	}

	@Override
	public Examination updateStatus(Long id, ExaminationStatus status) {
		Examination examination = examinationRepository.findById(id).orElse(null);
		if(examination == null) return null;
		
		examination.setStatus(status);
		return examinationRepository.save(examination);
	}

	@Override
	public void punishPatientAndUpdateExaminationStatus(Long id) {
		Examination examination = updateStatus(id, ExaminationStatus.NOT_REALIZED);
		if(examination != null)
			punishPatient(examination.getPatient());
		
	}
	
	private void punishPatient(Patient patient) {
		int penalties = patient.getPenalties() + 1;
		patient.setPenalties(penalties);
		userRepository.save(patient);
	}

	@Override
	public boolean updateInformation(Long examinationId, String infromation) {
		Examination examination = examinationRepository.findById(examinationId).orElse(null);
		if(examination != null && examination.getStatus().equals(ExaminationStatus.SCHEDULED)) {
			examination.setInformation(infromation);
			examinationRepository.save(examination);
			return true;
		}
		return false;
	}


	public List<PharmacyWithFreeAppointmentDTO> getFreePharmaciesAppointments(Date date) {
		List<Long> pharmacyIds = examinationRepository.getFreePharmaciesAppointments(date);
		List<PharmacyWithFreeAppointmentDTO> ret_list = new ArrayList<PharmacyWithFreeAppointmentDTO>();
		
		for(Long id : pharmacyIds) {
			Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);
			
			PharmacyWithFreeAppointmentDTO dto = new PharmacyWithFreeAppointmentDTO();
			dto.setId(id);
			dto.setName(pharmacy.getName());
			dto.setAddress(pharmacy.getAddress());
			
			dto.setPrice(examinationRepository.getPriceForAppointment(id));
			
			dto.setRate(examinationRepository.getAvgRateForPharmacy(id));
			
			ret_list.add(dto);
		}
		
		return ret_list;
		
	}

	@Override
	public List<PharmacistWithFreeAppointmentDTO> getFreePharmacistInPharmacy(Long id, Date date) {
		List<Long> pharmacistIds =  examinationRepository.getFreePharmacistInPharmacy(id, date);
		List<PharmacistWithFreeAppointmentDTO> ret_list = new ArrayList<PharmacistWithFreeAppointmentDTO>();
		
		for(Long pId : pharmacistIds) {
			PharmacistWithFreeAppointmentDTO dto = new PharmacistWithFreeAppointmentDTO();
			User user = userRepository.findById(pId).orElse(null);
			
			dto.setId(pId);
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setRate(examinationRepository.getAvgRateForHealthWorker(pId));
			
			ret_list.add(dto);
		}
		
		return ret_list;
		
	}

	@Override
	public boolean scheduleAtPharmacist(Long user_id, Long id, Date date) {
		if(userRepository.getPatientsPenalties(user_id) >= 3)
			return false;
		
		Examination ex = examinationRepository.getExaminationByPharmacistAndDate(id, date);
		ex.setPatient((Patient)(userRepository.findById(user_id).orElse(null)));
		ex.setStatus(ExaminationStatus.SCHEDULED);
		examinationRepository.save(ex);
		User user = userRepository.findById(user_id).orElse(null);
		emailService.sendNotificationAsync(user.getEmail(), "Scheduled appointment", 
				"You have successfully scheduled an appointment at pharmacist.");
		return true;
	}

	@Override
	public String createNewExaminations(NewExaminationsDTO newExaminations, Long userId) {
		PharmacyAdmin loggedUser = (PharmacyAdmin)userRepository.findById(userId).orElse(null);
		if(loggedUser == null)
			return "You are not logged in!";
		List<Long> dermatologistsPharmacies = userRepository.getPharmacyIdsForDermatologist(newExaminations.getHealthWorkerId());
		if(!dermatologistsPharmacies.contains(loggedUser.getPharmacy().getId())) {
			return "You can't create examination for health worker from another pharmacy!";
		}
		Integer countOfExams = examinationRepository.getNumFreeExaminationsForHealthWorkerInPharmacyInDate(
				newExaminations.getHealthWorkerId(), newExaminations.getDate(), loggedUser.getPharmacy().getId());
		if(countOfExams > 1) {
			return "This health worker already has created examinations for selected date!";
		}

		for (NewExaminationDTO newExam : newExaminations.getNewExaminations()) {
			Examination e = new Examination();
			e.setDate(newExaminations.getDate());
			e.setEndTime(newExam.getEndTime());
			e.setHealthWokrer((HealthWorker) userRepository.findById(newExaminations.getHealthWorkerId()).orElse(null));
			e.setInformation("");
			e.setMedicines(null);
			e.setPatient(null);
			e.setPharmacy(loggedUser.getPharmacy());
			e.setPrice(newExam.getPrice());
			e.setStartTime(newExam.getStartTime());
			e.setStatus(ExaminationStatus.CREATED);
			examinationRepository.save(e);
		}
		return "Successfully created examinations!";
	}

	@Override
	public List<Examination> getExaminationsFromHistoryByPatientToDate(Long patientId) {
		return examinationRepository.getExaminationsFromHistoryByPatientToDate(patientId);
	}
	
	@Override
	public List<ScheduledExaminationDTO> getPatientsFinishedAppointments(Long id) {
		List<Examination> examinations =  examinationRepository.getPatientsFinishedAppointments(id);
		List<ScheduledExaminationDTO> ret_list = new ArrayList<ScheduledExaminationDTO>();
		for(Examination ex : examinations) {
			double rate = examinationRepository.getAvgRateForHealthWorker(ex.getHealthWokrer().getId());
			ret_list.add(new ScheduledExaminationDTO(
					ex.getId(), ex.getStartTime().toString(), ex.getHealthWokrer().getId(),
					ex.getHealthWokrer().getFirstName(), ex.getHealthWokrer().getLastName(),
					ex.getHealthWokrer().getUserType().toString(),rate, ex.getPrice())
					);}
		return ret_list;
	}


	@Override
	public Pharmacy getPharmacyByExamination(Long examinationId) {
		return examinationRepository.getOne(examinationId).getPharmacy();
	}


	@Override
	public List<NewRateDTO> getHealthWorkersForRate(Long patientId) {
		List<Examination> examinations = examinationRepository.getPatientsFinishedAppointments(patientId);
		List<NewRateDTO> ret_list = new ArrayList<NewRateDTO>();
		NewRateDTO dto;
		
		for(Examination ex : examinations) {
			dto = new NewRateDTO();
			dto.setRateEntityId(ex.getHealthWokrer().getId());
			dto.setRateEntityName(ex.getHealthWokrer().getFirstName() + " " + ex.getHealthWokrer().getLastName());
			dto.setRateEntityType(ex.getHealthWokrer().getUserType().toString());
			dto.setRate(-1);
			
			if(!ret_list.contains(dto))
				ret_list.add(dto);
		}
		
		return ret_list;
	}
	
	@Override
	public List<Examination> getPatientsFinishedEx(Long patient_id){
		return examinationRepository.getPatientsFinishedAppointments(patient_id);
	}
}
