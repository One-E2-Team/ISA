package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithDoctorsMedicinesAndRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.TimeIntervalDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

public interface IPharmacyService {
	Pharmacy getById(Long id);
	Pharmacy save(Pharmacy pharmacy);
	List<Pharmacy> findAll();
	List<PharmacyDTO> findAllIPharmaciesDto();
	Pharmacy registerPharmacy(NewPharmacyDTO ph);
	PharmacyWithDoctorsMedicinesAndRateDTO getPharmacyById(Long id);
	List<NewRateDTO> getPharmaciesForRate(Long patient_id);
	Map<Date, Integer> getNumOfExamsByDateInPharmacy(TimeIntervalDTO interval, Long loggedUserId);
	Map<MedicineDTO, Integer> getNumOfMedicinesByDateInPharmacy(TimeIntervalDTO interval, Long loggedUserId);
	Double getPharmacyIncomeInTimeInterval(TimeIntervalDTO interval, Long loggedUserId);
}
