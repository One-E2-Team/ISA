package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.RequestForMissingMedicines;

public interface IRequestForMissingMedicinesRepository extends JpaRepository<RequestForMissingMedicines, Long> {
	
	@Query(value = "select * from requests_for_missing_medicines r where r.pharmacy_id = ?1", nativeQuery = true)
	List<RequestForMissingMedicines> getAllByPharmacy(Long pharmacyId);
}
