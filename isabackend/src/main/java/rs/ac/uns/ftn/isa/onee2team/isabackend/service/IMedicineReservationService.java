package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;
import java.util.Set;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestReservationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ReservedMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;

public interface IMedicineReservationService {

	List<MedicineReservation> findAllDoneReservationsByPatient(Long patientId);

	List<ReservedMedicineDTO> getPatientsReservations(Long patient_id);
	
	boolean cancelReservation(Long user_id, Long reservation_id);
	
	boolean reserve(RequestReservationDTO dto, Long patient_id);
	
	List<NewRateDTO> getMedicinesForRate(Long patientId);
	
	void takeMedicine(Long patientId, ReservedMedicineDTO dto);
	
	List<MedicineReservation> getAllMedicineReservations();
	
	Boolean takeReservationMedicine(Long reservationId);

	MedicineDTO getMedicineDTOfromReservation(Long reservationId,Long healthworker);
}
