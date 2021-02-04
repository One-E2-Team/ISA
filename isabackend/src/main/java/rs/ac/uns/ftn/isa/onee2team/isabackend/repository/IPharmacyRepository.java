package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacieWithMedicinesDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

public interface IPharmacyRepository extends JpaRepository<Pharmacy, Long> {
	@Query(value = "select id, name, address, description from pharmacies", nativeQuery = true)
	List<Pharmacy> findAll();
	
	@Query(value = "select NEW rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacieWithMedicinesDTO"
			+ "("
			+ " p.id , p.name , "
			+ "m.id , m.name \r\n" + ")" +
			"from Warehouse w, Pharmacy p, Medicine m\r\n" + 
			"where w.medicine = m and w.pharmacy = p")
	List<PharmacieWithMedicinesDTO> findAllIPharmaciesWithMedicines();
}
