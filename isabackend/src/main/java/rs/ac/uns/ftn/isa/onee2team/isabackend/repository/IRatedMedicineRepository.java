package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public interface IRatedMedicineRepository extends JpaRepository<RatedMedicine, Long> {

	List<RatedMedicine> findAllByMedicine(Medicine m);

	@Query("select r from RatedMedicine r where r.patient.id = ?1 and r.medicine.id = ?2")
	RatedMedicine getRatedMedicineByPatientAndMedicine(Long patient_id, Long medicine_id);
	
}
