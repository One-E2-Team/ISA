package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineReservationRepository;

@Service
public class MedicineReservationService implements IMedicineReservationService {

	private IMedicineReservationRepository medicineReservationRepository;
	
	@Autowired
	public MedicineReservationService(IMedicineReservationRepository medicineReservationRepository) {
		this.medicineReservationRepository = medicineReservationRepository;
	}
	
	@Override
	public List<MedicineReservation> findAllDoneReservationsByPatient(Long patientId) {
		return medicineReservationRepository.getDoneReservationsByPatient(patientId);
	}

}
