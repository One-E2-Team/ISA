package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyForSearchDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

public interface IPharmacyRepository extends JpaRepository<Pharmacy, Long> {
	@Query(value = "select id, name, address, description from pharmacies", nativeQuery = true)
	List<Pharmacy> findAll();
	
	@Query(value = "select NEW rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyForSearchDTO"
			+ "(p.id, p.name, p.address, p.description) from Pharmacy p")
	List<PharmacyForSearchDTO> findAllIPharmaciesDto();
	
	@Query(value = "select IFNULL(avg(rate),0) from rated_pharmacies where pharmacy_id = ?1", nativeQuery = true)
	double getAvgRateForPharmacy(Long pharmacy_id);
	
	@Query(value = "select * from pharmacies where id in \r\n" + 
			"(select distinct pharmacy_id from e_recipes where patient_id = ?1 and status in (0,1))", nativeQuery = true)
	List<Pharmacy> getPharmaciesFromERecipes(Long patientId);
}
