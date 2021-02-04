package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dermatologist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Pharmacist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	@Query(value = "select u from User u where u.userType = 0")
	List<Patient> getAllPatients();

	@Query(value = "select * from all_users u where u.user_type = 1", nativeQuery = true)
	List<Pharmacist> getAllPharmacists();

	@Query(value = "select * from all_users u where u.user_type = 1 and u.first_name like %?1% and u.last_name like %?2%", nativeQuery = true)
	List<Pharmacist> getAllPharmacistsByFirstAndLastName(String firstName, String lastName);

	@Query(value = "select * from all_users u where u.user_type = 1 and u.first_name like %?1% and u.last_name like %?2% "
			+ "and u.pharmacists_pharmacy_id = ?3", nativeQuery = true)
	List<Pharmacist> getAllPharmacistsByFirstAndLastNameAndPharmacyId(String firstName, String lastName,
			Long pharmacyId);

	@Query(value = "select * from all_users u where u.user_type = 2 and u.first_name like %?1% and u.last_name like %?2%", nativeQuery = true)
	List<Dermatologist> getAllDermatologistsByFirstAndLastName(String firstName, String lastName);

	@Query(value = "select * from all_users au where au.id in\r\n"
			+ "(select distinct u.id from all_users u, dermatologists_in_pharmacies d where d.dermatologist_id = u.id \r\n"
			+ "and u.user_type = 2 and u.first_name like %?1% and u.last_name like %?2% and d.pharmacies_id = ?3)", nativeQuery = true)
	List<Dermatologist> getAllDermatologistsByFirstAndLastNameAndPharmacyId(String firstName, String lastName,
			Long pharmacyId);
}
