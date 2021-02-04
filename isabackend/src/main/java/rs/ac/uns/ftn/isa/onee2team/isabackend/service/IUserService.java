package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.FirstLastNameDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserRequest;

public interface IUserService {
	List<User> getAll();
	List<Patient> getAllPatients();
	List<HealthWorkerDTO> getAllPharmacistsByFirstAndLastName(FirstLastNameDTO firstAndLastName);
	List<HealthWorkerDTO> getAllDermatologistsByFirstAndLastName(FirstLastNameDTO firstAndLastName);
	User save(UserRequest userRequest);
	User findByEmail(String email);
}
