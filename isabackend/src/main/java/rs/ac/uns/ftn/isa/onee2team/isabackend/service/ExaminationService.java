package rs.ac.uns.ftn.isa.onee2team.isabackend.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;

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
			ret_list.add(new ScheduledExaminationDTO(
						ex.getId(), ex.getStartTime().toString(), ex.getHealthWokrer().getId(), 
						ex.getHealthWokrer().getFirstName(), ex.getHealthWokrer().getLastName(), 
						ex.getHealthWokrer().getUserType().toString(),ex.getPrice()
					));
		}
		
		return ret_list;
	}

	@Override
	public void scheduleAtDermatologist(Long patientId, Long examinationId) {
		Examination examination =  examinationRepository.findById(examinationId).orElse(null);
		examination.setPatient((Patient)(userRepository.findById(patientId).orElse(null)));
		examination.setStatus(ExaminationStatus.SCHEDULED);
		examinationRepository.saveAndFlush(examination);
		User user = userRepository.findById(patientId).orElse(null);
		emailService.sendNotificationAsync(user.getEmail(), "Scheduled appointment", 
				"You have successfully scheduled an appointment at dermatologist.");
	}

	@Override
	public void cancelAppointment(Long examinationId) {
		Examination ex = examinationRepository.findById(examinationId).orElse(null);
		ex.setPatient(null);
		ex.setStatus(ExaminationStatus.CANCELED);
		examinationRepository.save(ex);
	}

	@Override
	public List<ScheduledExaminationDTO> getPatientsExaminations(Long patientId) {
		List<Examination> examinations =  examinationRepository.getScheduledAppointments(patientId);
		List<ScheduledExaminationDTO> ret_list = new ArrayList<ScheduledExaminationDTO>();
		for(Examination ex : examinations) {
			ret_list.add(new ScheduledExaminationDTO(
					ex.getId(), ex.getStartTime().toString(), ex.getHealthWokrer().getId(),
					ex.getHealthWokrer().getFirstName(), ex.getHealthWokrer().getLastName(),
					ex.getHealthWokrer().getUserType().toString(), ex.getPrice())
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
	public void updateInformation(Long examinationId, String infromation) {
		Examination examination = examinationRepository.findById(examinationId).orElse(null);
		if(examination != null && examination.getStatus().equals(ExaminationStatus.SCHEDULED)) {
			examination.setInformation(infromation);
			examinationRepository.save(examination);
		}
		
	}

}
