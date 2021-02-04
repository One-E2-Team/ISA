package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
	public List<HealthWorkerDTO> getAllPharmacistsByFirstAndLastName(@RequestBody FirstLastNameDTO firstAndLastName, Authentication authentication){
		User loggedUser = (User) authentication.getPrincipal();
		return userService.getAllPharmacistsByFirstAndLastName(firstAndLastName, loggedUser.getEmail());
	}
	
	@GetMapping(value = "/dermatologists")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')")
	public List<HealthWorkerDTO> getAllDermatologistsByFirstAndLastName(@RequestBody FirstLastNameDTO firstAndLastName, Authentication authentication){
		User loggedUser = (User) authentication.getPrincipal();
		return userService.getAllDermatologistsByFirstAndLastName(firstAndLastName, loggedUser.getEmail());
	}
}
