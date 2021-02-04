package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	
	@Query(value = "SELECT * FROM all_users u WHERE u.first_name LIKE %:firstname% and "
			+ "u.last_name LIKE %:lastname% and u.user_type = 0 ", nativeQuery = true )
	public List<Patient> getAllPatientsByFirstAndLastName(@Param("firstname")String firstname, @Param("lastname") String lastname);
}
