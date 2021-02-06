package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationAtDermatologistDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;

public interface IExaminationService {
	
	List<ExaminationDTO> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);

	List<ExaminationAtDermatologistDTO> getFreeExaminationsAtDermatologist();
	
	void scheduleAtDermatologist(Long patientId, Long examinationId);
}
