package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.CredentialsAndIdDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HireHealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.UserRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;

public interface IUserService {
	List<User> getAll();

	List<Patient> getAllPatients();

	List<SearchedPatientDTO> searchPatient(String firstName, String lastName);

	User createUser(UserRequestDTO userRequest, String role, UserType usertype);
	User createPatient(UserRequestDTO userRequest);
	User createDermatologist(UserRequestDTO userRequest);
	User createPharmacyAdmin(UserRequestDTO userRequest);
	User createDealer(UserRequestDTO userRequest);
	User createSystemAdmin(UserRequestDTO userRequest);

	List<HealthWorkerDTO> getAllPharmacistsByFirstAndLastName(String firstName, String lastName,
			String loggedUserEmail);

	List<HealthWorkerDTO> getAllDermatologistsByFirstAndLastName(String firstName, String lastName,
			String loggedUserEmail);

	User findByEmail(String email);

	User saveUser(User user);

	User findById(Long id);
	
	void changePassword(Long id, String password);
	
	User saveUserAndFlush(User user);
	
	List<CredentialsAndIdDTO> getAllFreePharmacists();
	
	List<CredentialsAndIdDTO> getDermatologistsWhoAreNotInPharmacy(Long loggedUserId);

	List<Long> getPatientAllergiesIds(Long patientId);
	
	Boolean hirePharmacist(HireHealthWorkerDTO hireWorker, Long loggedUserId);

	User saveDealerMWQ(Dealer d);
}
