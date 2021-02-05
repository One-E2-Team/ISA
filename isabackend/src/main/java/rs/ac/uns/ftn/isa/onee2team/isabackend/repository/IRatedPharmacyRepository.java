package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedPharmacy;

public interface IRatedPharmacyRepository extends JpaRepository<RatedPharmacy, Long> {
	@Query(value = "select IFNULL(avg(rate), 0) from rated_pharmacies p where p.pharmacy_id = ?1", nativeQuery = true)
	Double getAverageRateByPharmacyId(Long pharmacyId);
}
