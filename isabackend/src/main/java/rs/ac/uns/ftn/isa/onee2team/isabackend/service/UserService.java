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

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.DermatologistDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.FirstLastNameDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Authority;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dermatologist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Pharmacist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserRequest;
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
	public User save(UserRequest userRequest) {
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
		patient.setEnabled(true);

		List<Authority> auths = authService.findByname("ROLE_PATIENT");

		patient.setAuthorities(auths);

		patient = userRepository.save(patient);

		return patient;
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
	public List<PharmacistDTO> getAllPharmacistsByFirstAndLastName(FirstLastNameDTO firstAndLastName) {
		List<PharmacistDTO> ret = new ArrayList<PharmacistDTO>();
		for (Pharmacist pharmacist : userRepository.getAllPharmacistsByFirstAndLastName(firstAndLastName.getFirstName(),
				firstAndLastName.getLastName())) {
			PharmacistDTO dto = new PharmacistDTO();
			dto.setFirstName(pharmacist.getFirstName());
			dto.setLastName(pharmacist.getLastName());
			dto.setPharmacyName(pharmacist.getPharmacy().getName());
			List<Integer> rates = ratedHealthWorkerRepository.getRatesByHealthWorkerId(pharmacist.getId());
			dto.setRate(getAverageRate(rates));
			ret.add(dto);
		}
		return ret;
	}

	@Override
	public List<DermatologistDTO> getAllDermatologistsByFirstAndLastName(FirstLastNameDTO firstAndLastName) {
		List<DermatologistDTO> ret = new ArrayList<DermatologistDTO>();
		for (Dermatologist dermatologist : userRepository.getAllDermatologistsByFirstAndLastName(
				firstAndLastName.getFirstName(), firstAndLastName.getLastName())) {
			DermatologistDTO dto = new DermatologistDTO();
			dto.setFirstName(dermatologist.getFirstName());
			dto.setLastName(dermatologist.getLastName());
			List<Integer> rates = ratedHealthWorkerRepository.getRatesByHealthWorkerId(dermatologist.getId());
			dto.setRate(getAverageRate(rates));
			dto.setPharmacies(dermatologist.getPharmacies());
			ret.add(dto);
		}
		return ret;
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
