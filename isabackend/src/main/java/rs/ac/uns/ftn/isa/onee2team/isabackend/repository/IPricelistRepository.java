package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pricelist;

public interface IPricelistRepository extends JpaRepository<Pricelist, Long> {
	
	@Query(value = "select * from pricelists pr where pr.start_date < now() and pr.end_date > now() and pr.pharmacy_id = ?1", nativeQuery = true)
	List<Pricelist> getValidPricelist(Long pharmacyId);
}
