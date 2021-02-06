package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestWithHealthWorkerDTO;

public interface IVacationRequestService {

	void save(VacationRequestDTO vacationPromotion);

	List<VacationRequestWithHealthWorkerDTO> getAllVacationRequestsFromDermatologists();

	List<VacationRequestWithHealthWorkerDTO> getAllVacationRequestsFromPharmacists(Long adminId);

	Boolean declineVacationRequest(VacationRequestWithHealthWorkerDTO request);
}
