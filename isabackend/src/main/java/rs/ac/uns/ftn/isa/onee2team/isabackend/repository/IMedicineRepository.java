package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public interface IMedicineRepository extends JpaRepository<Medicine, Long> {
	
	/*
	@Query("select NEW rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO"
			+ "(m.id, m.code, m.name, m.contexture, m.manufacturer)" 
			+ "from Medicine m where m IN (select Medicine m from Warehouse w where w.pharmacy.getId() = ?1) ")
	public List<MedicineDTO> findMedicineByPharmacyid(Long id);*/
	
	@Query("select w.medicine from Warehouse w where w.pharmacy.id = ?1")
	public List<Medicine> findMedicineByPharmacyid(Long id);
}
