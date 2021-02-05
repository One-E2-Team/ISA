package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public interface IMedicineRepository extends JpaRepository<Medicine, Long> {
@Query("select w.medicine from Warehouse w where w.pharmacy.id = ?1")
	public List<Medicine> findMedicineByPharmacyid(Long id);

	@Query("select w.medicine.id from Warehouse w where w.pharmacy.id = ?1")
	public List<Integer> findMedicineIdsByPharmacyid(Long id);

}
