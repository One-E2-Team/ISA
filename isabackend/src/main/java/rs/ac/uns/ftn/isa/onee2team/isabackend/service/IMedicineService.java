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
	void addMissingMedicine(Long pharmacyId, Long medicineId);
	List<Medicine> findMedicineByPharmacyid(Long id);
	Boolean takeMedicine(Long pharmacyId, Long medicineId, Integer quantity);
	Boolean returnMedicine(Long pharmacyId, Long medicineId, Integer quantity);
	boolean WarehouseContainsMedicine(Long pharamacyId,Long medicineId, Integer quantity);
}
