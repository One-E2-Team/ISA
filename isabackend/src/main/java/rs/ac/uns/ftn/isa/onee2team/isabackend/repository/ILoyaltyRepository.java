package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Loyalty;

public interface ILoyaltyRepository extends JpaRepository<Loyalty, Long> {
	ArrayList<Loyalty> findAllByType(CategoryType ct);
	
	@Query("select l.type from Loyalty l where ?1 >= l.minPoints")
	List<CategoryType> getPatientType(Integer points);
	
	@Query("select l.discount from Loyalty l where l.type = ?1")
	double getDiscount(CategoryType type);
}
