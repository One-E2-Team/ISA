package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PatientsERecipeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public interface IERecipeService {

	List<PatientsERecipeDTO> getPatientsERecipes(Long patientId);
	
	List<Medicine> getMedicinesFromErecipesByPatient(Long patientId);
}
