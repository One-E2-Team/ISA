package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.CredentialsAndIdDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HireHealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewElevatedUserRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PatientProfileDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PatientsERecipeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyAdminProfileDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.StringInformationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.UserProfileDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IERecipeService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPromotionService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IUserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

	private IUserService userService;

	private PasswordEncoder passwordEncoder;

	private IPromotionService promotionService;
	
	private IERecipeService eRecipeService;

	@Autowired
	public UserController(IUserService userService, PasswordEncoder passwordEncoder,
			IPromotionService promotionService, IERecipeService eRecipeService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.promotionService = promotionService;
		this.eRecipeService = eRecipeService;
	}

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('PHARMACY_ADMIN')")
	public List<User> getAll() {
		return userService.getAll();
	}

	@GetMapping(value = "/me")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('PATIENT')" + "||"
			+ "hasRole('PHARMACIST')" + "||" + "hasRole('DERMATOLOGIST')" + "||" + "hasRole('DEALER')")
	public User getMe(Authentication auth) {
		return userService.findById(((User) auth.getPrincipal()).getId());
	}

	@GetMapping(value = "/patients")
	public List<Patient> getAllPatients() {
		return userService.getAllPatients();
	}

	@PutMapping(value = "/profile")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('ROLE_DERMATOLOGIST')"
			+ "||" + "hasRole('ROLE_PHARMACIST')" + "||" + "hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('DEALER')")
	public void updateUser(@RequestBody UserProfileDTO userDTO) {
		User user = userService.findById(userDTO.getId());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setAddress(userDTO.getAddress());
		user.setCity(userDTO.getCity());
		user.setPhoneNumber(userDTO.getPhone());
		userService.saveUser(user);
	}

	@GetMapping(value = "/logged")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('ROLE_DERMATOLOGIST')"
			+ "||" + "hasRole('ROLE_PHARMACIST')" + "||" + "hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('DEALER')")
	public UserProfileDTO getLoggedPatient() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();

		UserProfileDTO userDTO = new UserProfileDTO(user.getId(), user.getEmail(), user.getFirstName(),
				user.getLastName(), user.getAddress(), user.getCity(), user.getState(), user.getPhoneNumber());

		if (user.getUserType() == UserType.PATIENT) {
			Patient p = (Patient) auth.getPrincipal();
			List<CategoryType> cts = promotionService.getPatientType(p.getPoints());
			double discount = 0;
			String category = "";
			if (cts == null || cts.size() == 0) {
				category = "NO TYPE";
			} else {
				CategoryType ct = cts.get(cts.size() - 1);
				discount = promotionService.getDiscount(ct);
				category = ct.toString();
			}

			PatientProfileDTO patientDTO = new PatientProfileDTO(p.getId(), p.getEmail(), p.getFirstName(),
					p.getLastName(), p.getAddress(), p.getCity(), p.getState(), p.getPhoneNumber(), p.getPoints(),
					category, discount, p.getPenalties());
			return patientDTO;
		} else if (user.getUserType() == UserType.PHARMACY_ADMIN) {
			PharmacyAdmin pa = (PharmacyAdmin) auth.getPrincipal();
			PharmacyAdminProfileDTO pharmacyAdminDTO = new PharmacyAdminProfileDTO(pa.getId(), pa.getEmail(),
					pa.getFirstName(), pa.getLastName(), pa.getAddress(), pa.getCity(), pa.getState(),
					pa.getPhoneNumber(), pa.getPharmacy().getId());
			return pharmacyAdminDTO;
		}

		return userDTO;
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
		if (udto.getUserType().equalsIgnoreCase("PHARMACY_ADMIN"))
			return this.userService.createPharmacyAdmin(udto);
		else if (udto.getUserType().equalsIgnoreCase("DEALER"))
			return this.userService.createDealer(udto);
		else if (udto.getUserType().equalsIgnoreCase("DERMATOLOGIST"))
			return this.userService.createDermatologist(udto);
		else if (udto.getUserType().equalsIgnoreCase("SYSTEM_ADMIN"))
			return this.userService.createSystemAdmin(udto);
		else
			return null;
	}

	@PostMapping(value = "/checkPassword")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('ROLE_DERMATOLOGIST')"
			+ "||" + "hasRole('ROLE_PHARMACIST')" + "||" + "hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('DEALER')")
	public boolean checkPassword(@RequestBody StringInformationDTO sidto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return passwordEncoder.matches(sidto.getInfo(), user.getPassword());
	}

	@PostMapping(value = "/changePassword")
	@PreAuthorize("hasRole('PATIENT')" + "||" + "hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('ROLE_DERMATOLOGIST')"
			+ "||" + "hasRole('ROLE_PHARMACIST')" + "||" + "hasRole('SYSTEM_ADMIN')" + "||" + "hasRole('DEALER')")
	public void changePassword(@RequestBody StringInformationDTO sidto, Authentication auth) {
		userService.changePassword(((User) auth.getPrincipal()).getId(), passwordEncoder.encode(sidto.getInfo()));
	}
	
	@GetMapping(value ="/myAllergies")
	@PreAuthorize("hasRole('PATIENT')")
	public List<Long> getPatientAllergiesIds(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return userService.getPatientAllergiesIds(user.getId());
	}

	@GetMapping("/free-pharmacists")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<CredentialsAndIdDTO> getAllFreePharmacists() {
		return userService.getAllFreePharmacists();
	}

	@GetMapping(value = "/dermatologists-not-in-pharmacy")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<CredentialsAndIdDTO> getDermatologistsWhoAreNotInPharmacy(Authentication auth) {
		User user = (User) auth.getPrincipal();
		return userService.getDermatologistsWhoAreNotInPharmacy(user.getId());
	}
	
	@GetMapping(value ="/patient-allergies-ids/{id}")
	public List<Long> getPatientAllergiesIds(@PathVariable("id") Long patientId){
		return userService.getPatientAllergiesIds(patientId);
	}
	
	@GetMapping(value = "/myerecipes")
	@PreAuthorize("hasRole('PATIENT')")
	public List<PatientsERecipeDTO> getMyERecipes(Authentication auth) {
		User user = (User) auth.getPrincipal();
		return eRecipeService.getPatientsERecipes(user.getId());
	}
	
	@GetMapping(value = "/medicinesFromERecipes")
	@PreAuthorize("hasRole('PATIENT')")
	public List<Medicine> getMyMedicinesFromERecipes(Authentication auth) {
		User user = (User) auth.getPrincipal();
		return eRecipeService.getMedicinesFromErecipesByPatient(user.getId());
	}

	@PostMapping(value = "/hire-pharmacist")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public Boolean hirePharmacist(@RequestBody HireHealthWorkerDTO hireWorker, Authentication auth) {
		if(hireWorker.getWorkerId() == null || hireWorker.getStartDate().compareTo(hireWorker.getEndDate()) >= 0)
			return false;
		User user = (User) auth.getPrincipal();
		return userService.hirePharmacist(hireWorker, user.getId());
	}
	
	@PostMapping(value = "/updateDealerMWQ")
	@PreAuthorize("hasRole('DEALER')")
	public User updateDealerMWQ(@RequestBody Dealer d, Authentication auth) {
		if(((User) auth.getPrincipal()).getId().equals(d.getId()))
			return userService.saveDealerMWQ(d);
		return null;
	}
}
