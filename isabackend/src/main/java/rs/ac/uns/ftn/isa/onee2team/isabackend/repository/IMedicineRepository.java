package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public interface IMedicineRepository extends JpaRepository<Medicine, Long> {
	
	@Query("select w.medicine from Warehouse w where w.pharmacy.id = ?1 and w.amount > 0")
	public List<Medicine> findMedicineByPharmacyid(Long id);

	@Modifying
	@Transactional
	@Query(value = "insert into all_users_allergies (patient_id, allergies_id) values (?1, ?2)", nativeQuery = true)
	public void addAllergy(Long patientId, Long medicineId);


	@Query("select w.medicine.id from Warehouse w where w.pharmacy.id = ?1")
	public List<Long> findMedicineIdsByPharmacyid(Long id);
	
	@Query(value = "select * from medicines m where m.id in (\r\n" + 
			"select distinct medicine_id from medicines_with_quantity m  where m.id in (\r\n" + 
			"select medicines_with_quantity_id from e_recipes_medicines_with_quantity e "
			+ "where e.erecipe_id in ( select id from e_recipes where patient_id = ?1 )))", 
			nativeQuery = true)
	List<Medicine> getMedicinesFromErecipesByPatient(Long patientId);

}
