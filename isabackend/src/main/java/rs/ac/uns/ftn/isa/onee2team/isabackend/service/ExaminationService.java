package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class ExaminationService implements IExaminationService {

	private IExaminationRepository examinationRepository;
	private IUserRepository userRepository;
	private IEmailNotificationService emailService;

	@Autowired
	public ExaminationService(IExaminationRepository examinationRepository, IUserRepository userRepository,
			IEmailNotificationService emailService) {
		this.examinationRepository = examinationRepository;
		this.userRepository = userRepository;
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
		LocalDate now = LocalDate.now();
		System.out.println(ex.getStartTime());
		System.out.println(now);
		ex.setPatient(null);
		ex.setStatus(ExaminationStatus.CREATED);
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
}
