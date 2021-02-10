package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;

public interface IWarehouseRepository extends JpaRepository<Warehouse, Long> {

	@Query(value = "select distinct w.medicine_id from warehouses w where w.pharmacy_id = :pharmacyId", nativeQuery = true)
	List<Long> getAllMedicinesInPharmacy(@Param("pharmacyId") Long pharmacyId);
	
	@Query("select w from Warehouse w where w.pharmacy.id = ?1 and w.medicine.id = ?2")
	Warehouse getWarehouseByPharmacyAndMedicine(Long pharmacy_id, Long medicine_id);
	
	@Query(value = "select * from warehouses w where w.pharmacy_id = ?2 and w.medicine_id = ?1", nativeQuery = true)
	Warehouse getByMedicineAndPharmacy(Long medicineId, Long pharmacyId);
	
	@Query(value = "select * from warehouses w where w.medicine_id = ?1", nativeQuery = true)
	List<Warehouse> findAllByMedicineId(Long id);
	
	@Query(value = "select pharmacy_id from warehouses w where w.medicine_id = ?1 and w.amount - w.reserved_amount >= ?2", nativeQuery = true)
	List<Long> getAllPharmacyIdsWhereMedicineAmountIsAvailable(Long medicineId, Integer amount);
}
