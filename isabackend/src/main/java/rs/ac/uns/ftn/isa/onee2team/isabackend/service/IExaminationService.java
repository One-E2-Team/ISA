package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithFreeAppointmentDTO;

public interface IExaminationService {
	
	List<ExaminationDTO> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);

	List<ScheduledExaminationDTO> getFreeExaminationsAtDermatologist();
	
	void scheduleAtDermatologist(Long patientId, Long examinationId);
	
	void cancelAppointment(Long examinationId);
	
	List<ScheduledExaminationDTO> getPatientsExaminations(Long patientId);
	
	List<PharmacyWithFreeAppointmentDTO> getFreePharmaciesAppointments(Date date);
	
	List<PharmacistWithFreeAppointmentDTO> getFreePharmacistInPharmacy(Long id, Date date);
}
