package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.WorkingCalendar;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.CredentialsAndIdDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.HireHealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.SearchedPatientDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.UserRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Authority;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dermatologist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Pharmacist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.SystemAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRatedHealthWorkerRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWorkingCalendarRepository;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	private IUserRepository userRepository;
	private IWorkingCalendarRepository workingCalendarRepository;

	private IAuthorityService authService;
	private IRatedHealthWorkerRepository ratedHealthWorkerRepository;
	
	private IPromotionService promotionService;

	@Autowired
	public UserService(IUserRepository userRepository, IAuthorityService authService,
			IRatedHealthWorkerRepository ratedHealthWorkerRepository, 
			IWorkingCalendarRepository workingCalendarRepository, IPromotionService promotionService) {
		this.userRepository = userRepository;
		this.authService = authService;
		this.ratedHealthWorkerRepository = ratedHealthWorkerRepository;
		this.workingCalendarRepository = workingCalendarRepository;
		this.promotionService = promotionService;
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
	public User createUser(UserRequestDTO userRequest, String role, UserType usertype) {
		User user;
		switch (usertype) {
		case PATIENT:
			user = new Patient();
			break;
		case SYSTEM_ADMIN:
			user = new SystemAdmin();
			break;
		case PHARMACY_ADMIN:
			user = new PharmacyAdmin();
			break;
		case PHARMACIST:
			user = new Pharmacist();
			break;
		case DERMATOLOGIST:
			user = new Dermatologist();
			break;
		case DEALER:
			user = new Dealer();
			break;
		default:
			return null;
		}
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setFirstName(userRequest.getFirstname());
		user.setLastName(userRequest.getLastname());
		user.setAddress(userRequest.getAddress());
		user.setCity(userRequest.getCity());
		user.setState(userRequest.getState());
		user.setPhoneNumber(userRequest.getPhone());
		user.setUserType(usertype);
		user.setEnabled(false);
		List<Authority> auths = authService.findByname(role);

		user.setAuthorities(auths);

		user = userRepository.save(user);

		return user;
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User saveUserAndFlush(User user) {
		return userRepository.saveAndFlush(user);
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
		dto.setId(worker.getId());
		dto.setFirstName(worker.getFirstName());
		dto.setLastName(worker.getLastName());
		dto.setRate(ratedHealthWorkerRepository.getAverageRateByHealthWorkerId(worker.getId()));
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

	@Override
	public User createPatient(UserRequestDTO userRequest) {
		return createUser(userRequest, "ROLE_PATIENT", UserType.PATIENT);
	}

	@Override
	public User createDermatologist(UserRequestDTO userRequest) {
		return createUser(userRequest, "ROLE_DERMATOLOGIST", UserType.DERMATOLOGIST);
	}

	@Override
	public User createPharmacyAdmin(UserRequestDTO userRequest) {
		return createUser(userRequest, "ROLE_PHARMACY_ADMIN", UserType.PHARMACY_ADMIN);
	}

	@Override
	public User createDealer(UserRequestDTO userRequest) {
		return createUser(userRequest, "ROLE_DEALER", UserType.DEALER);
	}

	@Override
	public User createSystemAdmin(UserRequestDTO userRequest) {
		return createUser(userRequest, "ROLE_SYSTEM_ADMIN", UserType.SYSTEM_ADMIN);
	}

	@Override
	public void changePassword(Long id, String password) {
		userRepository.changePassword(id, password);
	}

	@Override
	public List<CredentialsAndIdDTO> getAllFreePharmacists() {
		List<CredentialsAndIdDTO> ret = new ArrayList<CredentialsAndIdDTO>();
		for (Pharmacist pharmacist : userRepository.getAllFreePharmacists()) {
			CredentialsAndIdDTO dto = new CredentialsAndIdDTO(pharmacist.getId(), pharmacist.getFirstName(),
					pharmacist.getLastName());
			ret.add(dto);
		}
		return ret;
	}

	@Override
	public List<CredentialsAndIdDTO> getDermatologistsWhoAreNotInPharmacy(Long loggedUserId) {
		List<CredentialsAndIdDTO> ret = new ArrayList<CredentialsAndIdDTO>();
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if (admin == null)
			return null;
		for (Dermatologist dermatologist : userRepository
				.getDermatologistsWhoAreNotInPharmacy(admin.getPharmacy().getId())) {
			CredentialsAndIdDTO dto = new CredentialsAndIdDTO(dermatologist.getId(), dermatologist.getFirstName(),
					dermatologist.getLastName());
			ret.add(dto);
		}
		return ret;
	}

	@Override
	public List<Long> getPatientAllergiesIds(Long patientId) {
		return userRepository.getPatientAllergiesIds(patientId);
	}

	@Override
	public Boolean hirePharmacist(HireHealthWorkerDTO hireWorker, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if(admin == null)
			return false;
		Pharmacist pharmacist = userRepository.getPharmacistById(hireWorker.getWorkerId());
		if(pharmacist == null || pharmacist.getPharmacy() != null)
			return false;
		pharmacist.setPharmacy(admin.getPharmacy());
		WorkingCalendar wc = new WorkingCalendar();
		wc.setPharmacy(admin.getPharmacy());
		wc.setEndDate(hireWorker.getEndDate());
		wc.setEndHour(hireWorker.getEndTime());
		wc.setHealthWorker(pharmacist);
		wc.setStartDate(hireWorker.getStartDate());
		wc.setStartHour(hireWorker.getStartTime());
		userRepository.save(pharmacist);
		workingCalendarRepository.save(wc);
		return true;
	}
	
	public double getDiscountForPatient(Long patientId) {
		Patient p = (Patient) userRepository.findById(patientId).get();
		List<CategoryType> ct = promotionService.getPatientType(p.getPoints());
		CategoryType type = ct.get(0);
		for (CategoryType categoryType : ct)
			if(type.ordinal() < categoryType.ordinal()) 
				type = categoryType;
		double discount = promotionService.getDiscount(type);
		return discount;
	}

	@Override
	public User saveDealerMWQ(Dealer d) {
		Dealer dealer = (Dealer) userRepository.findById(d.getId()).get();
		dealer.setMedicinesWithQuantity(d.getMedicinesWithQuantity());
		return userRepository.save(dealer);
	}

	@Override
	public Boolean hireDermatologist(HireHealthWorkerDTO hireWorker, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if(admin == null)
			return false;
		List<Long> dermatologistsPharmacies = userRepository.getPharmacyIdsForDermatologist(hireWorker.getWorkerId());
		if(dermatologistsPharmacies.contains(admin.getPharmacy().getId()))
			return false;
		Dermatologist dermatologist = userRepository.getDermatologistById(hireWorker.getWorkerId());
		if(dermatologist == null)
			return false;
		for(WorkingCalendar wc : workingCalendarRepository.getAllCalendarsForHealthWorker(hireWorker.getWorkerId())) {
			if(wc.getStartDate().after(hireWorker.getStartDate()) && wc.getEndDate().before(hireWorker.getEndDate())) {
				if(!isValidTime(wc.getStartHour(), wc.getEndHour(), hireWorker.getStartTime(), hireWorker.getEndTime()))
					return false;
			}
			if(hireWorker.getStartDate().after(wc.getStartDate()) && hireWorker.getEndDate().before(wc.getEndDate())) {
				if(!isValidTime(wc.getStartHour(), wc.getEndHour(), hireWorker.getStartTime(), hireWorker.getEndTime()))
					return false;
			}
			if(hireWorker.getStartDate().getTime() == wc.getStartDate().getTime() || hireWorker.getEndDate().getTime() == wc.getEndDate().getTime()) {
				if(!isValidTime(wc.getStartHour(), wc.getEndHour(), hireWorker.getStartTime(), hireWorker.getEndTime()))
					return false;
			}
			if(hireWorker.getStartDate().after(wc.getStartDate()) && hireWorker.getStartDate().before(wc.getEndDate())) {
				if(!isValidTime(wc.getStartHour(), wc.getEndHour(), hireWorker.getStartTime(), hireWorker.getEndTime()))
					return false;
			}
			if(wc.getStartDate().after(hireWorker.getStartDate()) && wc.getStartDate().before(hireWorker.getEndDate())) {
				if(!isValidTime(wc.getStartHour(), wc.getEndHour(), hireWorker.getStartTime(), hireWorker.getEndTime()))
					return false;
			}
		}
		dermatologist.getPharmacies().add(admin.getPharmacy());
		userRepository.save(dermatologist);
		WorkingCalendar workCal = new WorkingCalendar();
		workCal.setEndDate(hireWorker.getEndDate());
		workCal.setEndHour(hireWorker.getEndTime());
		workCal.setHealthWorker(dermatologist);
		workCal.setPharmacy(admin.getPharmacy());
		workCal.setStartDate(hireWorker.getStartDate());
		workCal.setStartHour(hireWorker.getStartTime());
		workingCalendarRepository.save(workCal);
		return true;
	}
	
	private Boolean isValidTime(Date startTimeWc, Date endTimeWc, Date startTimeWork, Date endTimeWork) {
		Calendar calWc = Calendar.getInstance();
		Calendar calWork = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT");
		calWc.setTime(startTimeWc);
		calWork.setTime(startTimeWork);
		calWc.setTimeZone(tz);
		calWork.setTimeZone(tz);
		int wcStartHour = calWc.get(Calendar.HOUR_OF_DAY);
		int workStartHour = calWork.get(Calendar.HOUR_OF_DAY);
		calWc.setTime(endTimeWc);
		calWork.setTime(endTimeWork);
		int wcEndHour = calWc.get(Calendar.HOUR_OF_DAY);
		int workEndHour = calWork.get(Calendar.HOUR_OF_DAY);
		if(wcStartHour == workStartHour)
			return false;
		if(wcStartHour > workStartHour && wcEndHour < workEndHour)
			return false;
		if(workStartHour > wcStartHour && workEndHour < wcEndHour)
			return false;
		if(wcStartHour > workStartHour && wcStartHour < workEndHour)
			return false;
		if(workStartHour > wcStartHour && workStartHour < wcEndHour)
			return false;
		return true;
	}

	@Override
	public List<PharmacyAdmin> getUnemployedPhAdmins() {
		return userRepository.getAllUnemployedPharmacyAdmins();
	}

	@Override
	public PharmacyAdmin employPhAdmin(PharmacyAdmin pa) {
		PharmacyAdmin user = (PharmacyAdmin) userRepository.findById(pa.getId()).get();
		if(user.getPharmacy() == null) {
			user.setPharmacy(pa.getPharmacy());
			return userRepository.save(user);
		}
		return null;
	}
}
