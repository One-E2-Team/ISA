package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.FirstLastNameDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.UserRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;

public interface IUserService {
	List<User> getAll();
	List<Patient> getAllPatients();
	User createPatient(UserRequestDTO userRequest);
	List<HealthWorkerDTO> getAllPharmacistsByFirstAndLastName(FirstLastNameDTO firstAndLastName, String loggedUserEmail);
	List<HealthWorkerDTO> getAllDermatologistsByFirstAndLastName(FirstLastNameDTO firstAndLastName, String loggedUserEmail);
	User findByEmail(String email);
	User saveUser(User user);
	User findById(Long id);
}
