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
	
}
