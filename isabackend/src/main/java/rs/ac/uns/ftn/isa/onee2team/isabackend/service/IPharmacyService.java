package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

public interface IPharmacyService {
	Pharmacy getById(Long id);
	Pharmacy save(Pharmacy pharmacy);
}
