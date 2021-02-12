package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ERecipeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.EditPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExamStatsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedStatsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineWithQuantityDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewPharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyForSearchDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithDoctorsMedicinesAndRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithPriceAndGradeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PresentMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.ERecipe;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.TimeIntervalDTO;
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
	public List<PharmacyForSearchDTO> getAllPharmaciesDto() {
		return pharmacyService.findAllIPharmaciesDto();
	}

	@GetMapping(value = "/medicinesByPharmacyId")
	public List<MedicineDTO> getMedicinesByPharmacyId(@RequestParam Long id) {
		return medicineService.findMedicineDTOByPharmacyid(id);
	}
	@GetMapping(value = "/{id}/medicines/")
	public List<Medicine> getMedicinesInPharmacy(@PathVariable("id") Long id) {
		return medicineService.findMedicineByPharmacyid(id);
	}
	@PostMapping(value = "/register")
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public Pharmacy registerPharmacy(@RequestBody NewPharmacyDTO phdto) {
		return pharmacyService.registerPharmacy(phdto);
	}

	@GetMapping(value = "/")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')")
	public PharmacyWithDoctorsMedicinesAndRateDTO getPharmacyById(@RequestParam Long id) {
		return pharmacyService.getPharmacyById(id);
	}
	

	@PostMapping(value="/{id}/take-medicine")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public Boolean takeMedicine(@RequestBody NewMedicineWithQuantityDTO medWithQuant, @PathVariable("id") Long pharmacyId ) {
		if(medicineService.takeMedicine(pharmacyId,medWithQuant.getMedicineId(),medWithQuant.getQuantity()))
			return true;
		return false;
	}
	
	@PostMapping(value="/{id}/return-medicine")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public Boolean returneMedicine(@RequestBody NewMedicineWithQuantityDTO medWithQuant, @PathVariable("id") Long pharmacyId ) {
		if(medicineService.returnMedicine(pharmacyId,medWithQuant.getMedicineId(),medWithQuant.getQuantity()))
			return true;
		return false;
	}

	@GetMapping(value = "/medicines/all")
	public List<PresentMedicineDTO> getMedicinesWithPrice(Authentication auth){
		return pharmacyService.getMedicinesWithPriceForUser(medicineService.getAllMedicinesWithRating(), auth == null ? 0 : (((User) auth.getPrincipal()).getUserType() != UserType.PATIENT ? 0 : ((User) auth.getPrincipal()).getId()));
	}
	
	@GetMapping(value = "/medicines/all/authorized")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('PHARMACIST')" + "||" + "hasRole('DERMATOLOGIST')" + "||" + "hasRole('DEALER')")
	public List<PresentMedicineDTO> getMedicinesWithPriceAuthenticated(Authentication auth){
		return getMedicinesWithPrice(auth);
	}
	
	

	@PostMapping(value = "/exam-stats")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<ExamStatsDTO> getNumOfExamsByDateInPharmacy(@RequestBody TimeIntervalDTO interval,
			Authentication auth) {
		if (interval.getStart().after(interval.getEnd()))
			return null;
		User user = (User) auth.getPrincipal();
		return pharmacyService.getNumOfExamsByDateInPharmacy(interval, user.getId());
	}

	@PostMapping(value = "/medicine-stats")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<MedStatsDTO> getNumOfMedicinesByDateInPharmacy(@RequestBody TimeIntervalDTO interval,
			Authentication auth) {
		if (interval.getStart().after(interval.getEnd()))
			return null;
		User user = (User) auth.getPrincipal();
		return pharmacyService.getNumOfMedicinesByDateInPharmacy(interval, user.getId());
	}

	@PostMapping(value = "/income")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public Double getPharmacyIncomeInTimeInterval(@RequestBody TimeIntervalDTO interval, Authentication auth) {
		if (interval.getStart().after(interval.getEnd()))
			return null;
		User user = (User) auth.getPrincipal();
		return pharmacyService.getPharmacyIncomeInTimeInterval(interval, user.getId());
	}
	
	@PostMapping(value = "/eRecipe/getAllWhereAvailable")
	@PreAuthorize("hasRole('PATIENT')")
	public List<PharmacyWithPriceAndGradeDTO> getAllWhereAvailableWithERecipe(@RequestBody ERecipeDTO erdto, Authentication auth){
		if(((User) auth.getPrincipal()).getId().equals(erdto.getPatientId()))
			return pharmacyService.getAllWhereAvailableWithERecipe(erdto);
		else return null;
	}
	
	@PostMapping(value = "/{id}/eRecipe/buy")
	@PreAuthorize("hasRole('PATIENT')")
	public ERecipe buyByERecipe(@PathVariable("id") Long pharmacyId, @RequestBody ERecipeDTO erdto, Authentication auth) {
		if(((User) auth.getPrincipal()).getId().equals(erdto.getPatientId()))
			return pharmacyService.buyByERecipe(pharmacyId, erdto, ((User) auth.getPrincipal()).getId());
		else return null;
	}
	@PutMapping(value = "/edit")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public Boolean editPharmacy(@RequestBody EditPharmacyDTO editPharmacy, Authentication auth) {
		User user = (User) auth.getPrincipal();
		return pharmacyService.editPharmacy(editPharmacy, user.getId());
	}
}
