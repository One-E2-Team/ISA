package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestForMissingMedicinesDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public interface IMedicineService {
	List<MedicineDTO> findMedicineDTOByPharmacyid(Long id);
	List<Medicine> getAll();
	void addAllergy(Long patientId, Long medicineId);
	Medicine createMedicine(NewMedicineDTO nmdto);
	List<RequestForMissingMedicinesDTO> getMissingMedicines(Long userId);
	Boolean deleteMedicineFromPharmacy(Long medicineId, Long loggedUserId);
	boolean reserveMedicine(Long pharmacyId, Long medicineId, Integer quantity);
	void addMissingMedicine(Long pharmacyId, Long medicineId);
	List<Medicine> findMedicineByPharmacyid(Long id);
}
