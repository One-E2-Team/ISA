package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.UserRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Authority;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dermatologist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Pharmacist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRatedHealthWorkerRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	private IUserRepository userRepository;

	private IAuthorityService authService;
	private IRatedHealthWorkerRepository ratedHealthWorkerRepository;

	@Autowired
	public UserService(IUserRepository userRepository, IAuthorityService authService,
			IRatedHealthWorkerRepository ratedHealthWorkerRepository) {
		this.userRepository = userRepository;
		this.authService = authService;
		this.ratedHealthWorkerRepository = ratedHealthWorkerRepository;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public List<Patient> getAllPatients() {
		return userRepository.getAllPatients();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User createPatient(UserRequestDTO userRequest) {
		Patient patient = new Patient();
		patient.setEmail(userRequest.getEmail());
		patient.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		patient.setFirstName(userRequest.getFirstname());
		patient.setLastName(userRequest.getLastname());
		patient.setAddress(userRequest.getAddress());
		patient.setCity(userRequest.getCity());
		patient.setState(userRequest.getState());
		patient.setPhoneNumber(userRequest.getPhone());
		patient.setUserType(UserType.PATIENT);
		patient.setEnabled(false);
		List<Authority> auths = authService.findByname("ROLE_PATIENT");

		patient.setAuthorities(auths);

		patient = userRepository.save(patient);

		return patient;
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}

	@Override
	public List<SearchedPatientDTO> searchPatient(String firstName, String lastName) {
		return userRepository.getAllPatientsByFirstAndLastName(firstName, lastName);
	}

	@Override
	public List<HealthWorkerDTO> getAllPharmacistsByFirstAndLastName(String firstName, String lastName,
			String loggedUserEmail) {
		List<HealthWorkerDTO> ret = new ArrayList<HealthWorkerDTO>();
		List<Pharmacist> allPharmacists = new ArrayList<Pharmacist>();
		User loggerUser = getLoggedUser(loggedUserEmail);
		UserType loggedUserRole = loggerUser.getUserType();
		if (loggedUserRole.equals(UserType.PATIENT)) {
			allPharmacists = userRepository.getAllPharmacistsByFirstAndLastName(firstName, lastName);
		} else if (loggedUserRole.equals(UserType.PHARMACY_ADMIN)) {
			allPharmacists = userRepository.getAllPharmacistsByFirstAndLastNameAndPharmacyId(firstName, lastName,
					((PharmacyAdmin) loggerUser).getPharmacy().getId());
		}
		for (Pharmacist pharmacist : allPharmacists) {
			ret.add(getProperDTO(pharmacist, true));
		}
		return ret;
	}

	@Override
	public List<HealthWorkerDTO> getAllDermatologistsByFirstAndLastName(String firstName, String lastName,
			String loggedUserEmail) {
		List<HealthWorkerDTO> ret = new ArrayList<HealthWorkerDTO>();
		List<Dermatologist> allDermatologists = new ArrayList<Dermatologist>();
		User loggerUser = getLoggedUser(loggedUserEmail);
		UserType loggedUserRole = loggerUser.getUserType();
		if (loggedUserRole.equals(UserType.PATIENT)) {
			allDermatologists = userRepository.getAllDermatologistsByFirstAndLastName(firstName, lastName);
		} else if (loggedUserRole.equals(UserType.PHARMACY_ADMIN)) {
			allDermatologists = userRepository.getAllDermatologistsByFirstAndLastNameAndPharmacyId(firstName, lastName,
					((PharmacyAdmin) loggerUser).getPharmacy().getId());
		}
		for (Dermatologist dermatologist : allDermatologists) {
			ret.add(getProperDTO(dermatologist, false));
		}
		return ret;
	}

	private User getLoggedUser(String loggedUserEmail) {
		return userRepository.findByEmail(loggedUserEmail);
	}

	private HealthWorkerDTO getProperDTO(HealthWorker worker, Boolean isPharmacist) {
		HealthWorkerDTO dto = new HealthWorkerDTO();
		dto.setFirstName(worker.getFirstName());
		dto.setLastName(worker.getLastName());
		List<Integer> rates = ratedHealthWorkerRepository.getRatesByHealthWorkerId(worker.getId());
		dto.setRate(getAverageRate(rates));
		dto.setPharmacyNames(new ArrayList<String>());
		if (isPharmacist) {
			dto.getPharmacyNames().add(((Pharmacist) worker).getPharmacy().getName());
		} else {
			for (Pharmacy pharmacy : ((Dermatologist) worker).getPharmacies()) {
				dto.getPharmacyNames().add(pharmacy.getName());
			}
		}
		return dto;
	}

	private Double getAverageRate(List<Integer> rates) {
		Double rate = 0.0;
		for (Integer oneRate : rates) {
			rate += oneRate;
		}
		if (!rates.isEmpty()) {
			rate /= rates.size();
		}
		return rate;
	}
}
