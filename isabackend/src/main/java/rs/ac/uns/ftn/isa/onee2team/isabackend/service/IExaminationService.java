package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;

public interface IExaminationService {
	
	List<ExaminationDTO> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);

	List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status);
	List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status,Long pharmacyId);


	List<ScheduledExaminationDTO> getFreeExaminationsAtDermatologist();
	
	void scheduleAtDermatologist(Long patientId, Long examinationId);
	
	void cancelAppointment(Long examinationId);
	
	List<ScheduledExaminationDTO> getPatientsExaminations(Long patientId);

}
