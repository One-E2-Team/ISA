package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.UserProfileDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;

public interface IExaminationService {
	
	List<ExaminationDTO> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);
	List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status);
	List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status,Long pharmacyId);


	List<ScheduledExaminationDTO> getFreeExaminationsAtDermatologist(Long patientId);
	
	String scheduleExamination(Long patientId, Long examinationId);
	
	void cancelAppointment(Long examinationId);
	
	List<ScheduledExaminationDTO> getPatientsExaminations(Long patientId);

	Patient getPatientFromExamination(Long id);

	Examination updateStatus(Long id, ExaminationStatus status);

	void punishPatientAndUpdateExaminationStatus(Long id);

	boolean updateInformation(Long examinationId, String infromation);
	
	List<PharmacyWithFreeAppointmentDTO> getFreePharmaciesAppointments(Date date);
	
	List<PharmacistWithFreeAppointmentDTO> getFreePharmacistInPharmacy(Long id, Date date);
	
	String scheduleAtPharmacist(Long user_id, Long id, Date date);
	
	String createNewExaminations(NewExaminationsDTO newExaminations, Long userId);
	
	List<Examination> getExaminationsFromHistoryByPatientToDate(Long patientId);
	
	List<ScheduledExaminationDTO> getPatientsFinishedAppointments(Long id);


	Pharmacy getPharmacyByExamination(Long examinationId);

	
	List<NewRateDTO> getHealthWorkersForRate(Long patientId);
	
	List<Examination> getPatientsFinishedEx(Long patient_id);
	
	List<ExaminationDTO> searchAllFreeExaminationsInSpecificDays(Long pharmacyId, Date start, Date end,Long healthworkerId);
	Boolean finishExamination(Long examinationId);
	
	List<UserProfileDTO> getExaminedPatientsByHealthWorkerId(Long healthworkerId);
	List<ExaminationDTO> getAllExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId, Date start, Date end);
}
