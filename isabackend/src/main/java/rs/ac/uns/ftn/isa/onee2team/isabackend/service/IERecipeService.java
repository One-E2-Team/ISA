package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PatientsERecipeDTO;

public interface IERecipeService {

	List<PatientsERecipeDTO> getPatientsERecipes(Long patientId);
}
