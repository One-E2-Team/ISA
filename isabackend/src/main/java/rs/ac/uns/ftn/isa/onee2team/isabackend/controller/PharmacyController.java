package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithDoctorsMedicinesAndRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PresentMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IMedicineService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPharmacyService;

@RestController
@RequestMapping(value = "api/pharmacies")
public class PharmacyController {

	private IPharmacyService pharmacyService;
	private IMedicineService medicineService;

	@Autowired
	public PharmacyController(IPharmacyService pharmacyService, IMedicineService medicineService) {
		this.pharmacyService = pharmacyService;
		this.medicineService = medicineService;
	}

//	@GetMapping
//	public Pharmacy getById(Long id) {
//		return pharmacyService.getById(id);
//	}

	@PostMapping
	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyService.save(pharmacy);
	}

	@GetMapping(value = "/all")
	public List<Pharmacy> getAll() {
		return pharmacyService.findAll();
	}

	@GetMapping(value = "/pharmaciesDto")
	public List<PharmacyDTO> getAllPharmaciesDto() {
		return pharmacyService.findAllIPharmaciesDto();
	}

	@GetMapping(value = "/medicinesByPharmacyId")
	public List<MedicineDTO> getMedicinesByPharmacyId(@RequestParam Long id) {
		return medicineService.findMedicineByPharmacyid(id);
	}
	
	@PostMapping(value = "/register")
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public Pharmacy registerPharmacy(@RequestBody NewPharmacyDTO phdto) {
		return pharmacyService.registerPharmacy(phdto);
	}
  
	@GetMapping(value = "/")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')")
	public PharmacyWithDoctorsMedicinesAndRateDTO getPharmacyById(@RequestParam Long id){
		return pharmacyService.getPharmacyById(id);
	}
	
	@GetMapping(value = "/medicines/all")
	public List<PresentMedicineDTO> getMedicinesWithPrice(Authentication auth){
		return pharmacyService.getMedicinesWithPriceForUser(medicineService.getAllMedicinesWithRating(), auth == null ? 0 : (((User) auth.getPrincipal()).getUserType() != UserType.PATIENT ? 0 : ((User) auth.getPrincipal()).getId()));
	}
	
	@GetMapping(value = "/medicines/all/authorized")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('PHARMACIST')" + "||" + "hasRole('DERMATOLOGIST')")
	public List<PresentMedicineDTO> getMedicinesWithPriceAuthenticated(Authentication auth){
		return getMedicinesWithPrice(auth);
	}
	
	
}
