package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;

public interface IMedicineReservationRepository extends JpaRepository<MedicineReservation, Long> {

	@Query(value = "select * from medicine_reservations where patient_id = ?1 and status = 2", nativeQuery = true)
	List<MedicineReservation> getDoneReservationsByPatient(Long patientId);
	
	@Query("select m from MedicineReservation m where m.patient.id = ?1 and m.status = 0")
	List<MedicineReservation> getPatientsReservations(Long patient_id);
}
