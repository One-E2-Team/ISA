package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedHealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedPharmacy;

public interface IRatedPharmacyRepository extends JpaRepository<RatedPharmacy, Long> {
	@Query(value = "select IFNULL(avg(rate), 0) from rated_pharmacies p where p.pharmacy_id = ?1", nativeQuery = true)
	Double getAverageRateByPharmacyId(Long pharmacyId);
	
	@Query("select r from RatedPharmacy r where r.patient.id = ?1 and r.pharmacy.id = ?2")
	public RatedPharmacy getRatedPharmacyByPatientAndPharmacy(Long patient_id, Long pharmacy_id);
}
