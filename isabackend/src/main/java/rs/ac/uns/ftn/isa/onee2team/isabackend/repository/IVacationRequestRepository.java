package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.VacationRequest;

public interface IVacationRequestRepository extends JpaRepository<VacationRequest, Long> {

	@Query(value = "select * from vacation_requests v where v.accepted is null and v.health_wokrer_id in "
			+ "(select u.id from all_users u where u.user_type = 2)", nativeQuery = true)
	List<VacationRequest> getAllVacationRequestsFromDermatologists();
	
	@Query(value = "select * from vacation_requests v where v.accepted is null and v.health_wokrer_id in\r\n"
			+ "(select u.id from all_users u where u.user_type = 1 and u.pharmacists_pharmacy_id = ?1)", nativeQuery = true)
	List<VacationRequest> getAllVacationRequestsFromPharmacistsByPharmacyId(Long pharmacyId);
}
