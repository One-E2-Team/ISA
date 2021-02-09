package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public interface IRatedMedicineRepository extends JpaRepository<RatedMedicine, Long> {

	List<RatedMedicine> findAllByMedicine(Medicine m);
}
