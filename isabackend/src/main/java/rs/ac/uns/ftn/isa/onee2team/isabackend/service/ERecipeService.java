package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PatientsERecipeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.ERecipe;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IERecipeRepository;

@Service
public class ERecipeService implements IERecipeService {
	
	private IERecipeRepository eRecipeRepository;
	
	@Autowired
	public ERecipeService(IERecipeRepository eRecipeRepository) {
		this.eRecipeRepository = eRecipeRepository;
	}

	@Override
	public List<PatientsERecipeDTO> getPatientsERecipes(Long patientId) {
		List<ERecipe> recipes = eRecipeRepository.getPatientsERecipes(patientId);
		List<PatientsERecipeDTO> ret = new ArrayList<PatientsERecipeDTO>();
		
		for (ERecipe e : recipes) {
			PatientsERecipeDTO dto = new PatientsERecipeDTO();
			dto.setId(e.getId());
			dto.setCode(e.getCode());
			dto.setDate(e.getDate());
			dto.setPharmacyName(e.getPharmacy().getName());
			dto.setStatus(e.getStatus().toString());
			
			ret.add(dto);
		}
		
		return ret;
	}

}
