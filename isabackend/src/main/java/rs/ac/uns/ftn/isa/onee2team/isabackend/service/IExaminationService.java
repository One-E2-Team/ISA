package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;

public interface IExaminationService {
	
	List<ExaminationDTO> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);

	List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status);
	List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status,Long pharmacyId);


	List<ScheduledExaminationDTO> getFreeExaminationsAtDermatologist();
	
	void scheduleAtDermatologist(Long patientId, Long examinationId);
	
	void cancelAppointment(Long examinationId);
	
	List<ScheduledExaminationDTO> getPatientsExaminations(Long patientId);
	
	List<PharmacyWithFreeAppointmentDTO> getFreePharmaciesAppointments(Date date);
	
	List<PharmacistWithFreeAppointmentDTO> getFreePharmacistInPharmacy(Long id, Date date);
	
	void scheduleAtPharmacist(Long user_id, Long id, Date date);
	
	String createNewExaminations(NewExaminationsDTO newExaminations, Long userId);
	
	List<Examination> getExaminationsFromHistoryByPatientToDate(Long patientId);
}
