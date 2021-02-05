package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

public interface IPharmacyRepository extends JpaRepository<Pharmacy, Long> {
	@Query(value = "select id, name, address, description from pharmacies", nativeQuery = true)
	List<Pharmacy> findAll();
	
	@Query(value = "select NEW rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyDTO"
			+ "(p.id, p.name, p.address, p.description) from Pharmacy p")
	List<PharmacyDTO> findAllIPharmaciesDto();
}
