package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.FirstLastNameDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IUserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

	private IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('PHARMACY_ADMIN')")
	public List<User> getAll() {
		return userService.getAll();
	}

	@GetMapping(value = "/patients")
	public List<Patient> getAllPatients() {
		return userService.getAllPatients();
	}
	
	@GetMapping(value = "/pharmacists")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')")
	public List<HealthWorkerDTO> getAllPharmacistsByFirstAndLastName(@RequestBody FirstLastNameDTO firstAndLastName){
		return userService.getAllPharmacistsByFirstAndLastName(firstAndLastName);
	}
	

	@GetMapping(value = "/patients/search")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public List<Patient> searchPatient(@RequestBody FirstLastNameDTO firstAndLastName){
		return userService.searchPatient(firstAndLastName);
	}

	@GetMapping(value = "/dermatologists")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')")
	public List<HealthWorkerDTO> getAllDermatologistsByFirstAndLastName(@RequestBody FirstLastNameDTO firstAndLastName){
		return userService.getAllDermatologistsByFirstAndLastName(firstAndLastName);

	}
}
