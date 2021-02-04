package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacieWithMedicinesDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;

@Service
public class PharmacyService implements IPharmacyService{
	
	private IPharmacyRepository pharmacyRepository;
	
	@Autowired
	public PharmacyService(IPharmacyRepository pharmacyRepository) {
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public Pharmacy getById(Long id) {
		return pharmacyRepository.findById(id).orElse(null);
	}

	@Override
	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyRepository.save(pharmacy);
	}
	
	@Override
	public List<Pharmacy> findAll(){
		return pharmacyRepository.findAll();
	}

	@Override
	public List<PharmacieWithMedicinesDTO> findAllPharmaciesWithMedicines() {
		return pharmacyRepository.findAllIPharmaciesWithMedicines();
	}
}
