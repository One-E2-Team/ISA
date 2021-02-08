package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestReservationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ReservedMedicineDTO;

public interface IMedicineReservationService {

	List<ReservedMedicineDTO> getPatientsReservations(Long patient_id);
	
	boolean cancelReservation(Long user_id, Long reservation_id);
	
	boolean reserve(RequestReservationDTO dto, Long patient_id);
}
