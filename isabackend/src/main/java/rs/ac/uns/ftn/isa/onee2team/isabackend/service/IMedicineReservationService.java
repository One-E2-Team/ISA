package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;

public interface IMedicineReservationService {

	List<MedicineReservation> findAllDoneReservationsByPatient(Long patientId);
}
