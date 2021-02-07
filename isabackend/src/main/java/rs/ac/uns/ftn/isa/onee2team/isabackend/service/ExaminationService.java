package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class ExaminationService implements IExaminationService {

	private IExaminationRepository examinationRepository;
	private IUserRepository userRepository;
	private IEmailNotificationService emailService;
	private IPharmacyRepository pharmacyRepository;

	@Autowired
	public ExaminationService(IExaminationRepository examinationRepository, IUserRepository userRepository,
			IEmailNotificationService emailService, IPharmacyRepository pharmacyRepository) {
		this.examinationRepository = examinationRepository;
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.pharmacyRepository = pharmacyRepository;
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
}
