package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacieWithMedicinesDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPharmacyService;

@RestController
@RequestMapping(value = "api/pharmacies")
public class PharmacyController {

	private IPharmacyService pharmacyService;

	@Autowired
	public PharmacyController(IPharmacyService pharmacyService) {
		this.pharmacyService = pharmacyService;
	}

	@GetMapping
	public Pharmacy getById(Long id) {
		return pharmacyService.getById(id);
	}
	
	@PostMapping
	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyService.save(pharmacy);
	}
	
	@GetMapping(value = "/all")
	public List<Pharmacy> getAll() {
		return pharmacyService.findAll();
	}
	
	@GetMapping(value = "/pharmaciesWithMedicines")
	public List<PharmacieWithMedicinesDTO> getAllPharmaciesWithMedicines() {
		return pharmacyService.findAllPharmaciesWithMedicines();
	}
}
