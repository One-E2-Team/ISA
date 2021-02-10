package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.ERecipe;

public interface IERecipeRepository extends JpaRepository<ERecipe, Long> {

	@Query(value =  "select * from e_recipes e where e.code = ?1 and e.status = 2", nativeQuery = true)
	List<ERecipe> findByCodeNotRejected(String code);
}
