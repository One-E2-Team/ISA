package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewElevatedUserRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO;
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
	public List<HealthWorkerDTO> getAllPharmacistsByFirstAndLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		return userService.getAllPharmacistsByFirstAndLastName(firstName, lastName, loggedUser.getEmail());
	}

	@GetMapping(value = "/patients/search")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public List<SearchedPatientDTO> searchPatient(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {
		return userService.searchPatient(firstName, lastName);
	}

	@GetMapping(value = "/dermatologists")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')")

	public List<HealthWorkerDTO> getAllDermatologistsByFirstAndLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		return userService.getAllDermatologistsByFirstAndLastName(firstName, lastName, loggedUser.getEmail());

	}
	
	@PostMapping(value = "/registerElevatedUser")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public User registerElevatedUser(@RequestBody NewElevatedUserRequestDTO udto) {
		if(udto.getUserType().equalsIgnoreCase("PHARMACY_ADMIN"))
			return this.userService.createPharmacyAdmin(udto);
		else if(udto.getUserType().equalsIgnoreCase("DEALER"))
			return this.userService.createDealer(udto);
		else if(udto.getUserType().equalsIgnoreCase("DERMATOLOGIST"))
			return this.userService.createDermatologist(udto);
		else if(udto.getUserType().equalsIgnoreCase("SYSTEM_ADMIN"))
			return this.userService.createSystemAdmin(udto);
		else return null;
	}
}
