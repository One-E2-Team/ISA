package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.ERecipe;

public interface IERecipeRepository extends JpaRepository<ERecipe, Long> {

	@Query("select e from ERecipe e where e.patient.id = ?1")
	List<ERecipe> getPatientsERecipes(Long patientId);
}
