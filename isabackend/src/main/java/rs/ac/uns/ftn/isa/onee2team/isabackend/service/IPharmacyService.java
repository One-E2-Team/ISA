package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

public interface IPharmacyService {
	Pharmacy getById(Long id);
	Pharmacy save(Pharmacy pharmacy);
	List<Pharmacy> findAll();
	List<PharmacyDTO> findAllIPharmaciesDto();
}
