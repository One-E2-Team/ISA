package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineWithQuantityDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestForMissingMedicinesDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IMedicineService;

@RestController
@RequestMapping(value = "/api/medicines")
public class MedicineController {

	private IMedicineService medicineService;

	@Autowired
	public MedicineController(IMedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('PATIENT')" + "||" + "hasRole('DEALER')")
	public List<Medicine> getAll() {
		return medicineService.getAll();
	}
	
	@PostMapping(value = "/addAllergy/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public void addAllergy(@PathVariable("id") Long medicineId) {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		medicineService.addAllergy(user.getId(), medicineId);
	}

	@PostMapping(value = "/create")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public Medicine createMedicine(@RequestBody NewMedicineDTO nmdto) {
		return medicineService.createMedicine(nmdto);
	}
	
	@GetMapping(value = "/missing")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<RequestForMissingMedicinesDTO> getMissingMedicines(Authentication auth){
		User user = (User) auth.getPrincipal();
		return medicineService.getMissingMedicines(user.getId());
	}
	
	@DeleteMapping(value = "/delete-from-pharmacy")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public Boolean deleteMedicineFromPharmacy(@RequestParam Long medicineId, Authentication auth) {
		User user = (User) auth.getPrincipal();
		return medicineService.deleteMedicineFromPharmacy(medicineId, user.getId());
	}
}
