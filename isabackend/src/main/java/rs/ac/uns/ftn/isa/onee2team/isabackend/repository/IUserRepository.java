package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO;
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
	
	@Query(value = "select * from all_users u where u.user_type = 1 and u.pharmacists_pharmacy_id = ?1", nativeQuery = true)
	List<Pharmacist> getAllPharmacistsByPharmacyId(Long pharmacyId);

	@Query(value = "select * from all_users u where u.user_type = 1 and u.first_name like %?1% and u.last_name like %?2%", nativeQuery = true)
	List<Pharmacist> getAllPharmacistsByFirstAndLastName(String firstName, String lastName);

	@Query(value = "SELECT NEW rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO (u.id, u.firstName, u.lastName, u.email, u.phoneNumber) from User u WHERE u.firstName LIKE %:firstname% and "
			+ "u.lastName LIKE %:lastname% and u.userType = 0 ")
	public List<SearchedPatientDTO> getAllPatientsByFirstAndLastName(@Param("firstname") String firstname,
			@Param("lastname") String lastname);

	@Query(value = "select * from all_users u where u.user_type = 1 and u.first_name like %?1% and u.last_name like %?2% "
			+ "and u.pharmacists_pharmacy_id = ?3", nativeQuery = true)
	List<Pharmacist> getAllPharmacistsByFirstAndLastNameAndPharmacyId(String firstName, String lastName,
			Long pharmacyId);
	
	@Query(value = "select * from all_users au where au.id in\r\n"
			+ "(select distinct u.id from all_users u, dermatologists_in_pharmacies d where d.dermatologist_id = u.id \r\n"
			+ "and u.user_type = 2 and d.pharmacies_id = ?1)", nativeQuery = true)
	List<Dermatologist> getAllDermatologistsByPharmacyId(Long pharmacyId);

	@Query(value = "select * from all_users u where u.user_type = 2 and u.first_name like %?1% and u.last_name like %?2%", nativeQuery = true)
	List<Dermatologist> getAllDermatologistsByFirstAndLastName(String firstName, String lastName);

	@Query(value = "select * from all_users au where au.id in\r\n"
			+ "(select distinct u.id from all_users u, dermatologists_in_pharmacies d where d.dermatologist_id = u.id \r\n"
			+ "and u.user_type = 2 and u.first_name like %?1% and u.last_name like %?2% and d.pharmacies_id = ?3)", nativeQuery = true)
	List<Dermatologist> getAllDermatologistsByFirstAndLastNameAndPharmacyId(String firstName, String lastName,
			Long pharmacyId);
	
	@Modifying
	@Transactional
	@Query(value = "update all_users set password = ?2 where id = ?1", nativeQuery = true)
	void changePassword(Long id, String password);
	
	@Query(value = "select d.pharmacies_id from dermatologists_in_pharmacies d where d.dermatologist_id = ?1", nativeQuery = true)
	List<Long> getPharmacyIdsForDermatologist(Long dermatologistId);
	
	@Query(value = "select penalties from all_users where id = ?1", nativeQuery = true)
	Integer getPatientsPenalties(Long id);

	@Query(value = "select * from all_users u where u.pharmacists_pharmacy_id is null and u.user_type = 1", nativeQuery = true)
	List<Pharmacist> getAllFreePharmacists();
	
	@Query(value = "select * from all_users u where u.user_type = 2 and u.id not in "
			+ "(select d.dermatologist_id from dermatologists_in_pharmacies d where d.pharmacies_id = ?1)", nativeQuery = true)
	List<Dermatologist> getDermatologistsWhoAreNotInPharmacy(Long pharmacyId);

	@Query(value = "select allergies_id from all_users_allergies where patient_id = ?1", nativeQuery = true)
	List<Long> getPatientAllergiesIds(Long patientId);
	
	@Query(value = "select * from all_users u where u.id = ?1 and u.user_type = 1", nativeQuery = true)
	Pharmacist getPharmacistById(Long id);
}
