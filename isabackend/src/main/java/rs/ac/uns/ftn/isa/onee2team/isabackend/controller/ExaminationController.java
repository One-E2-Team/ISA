package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationInformationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduleNewExanimationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.TimeIntervalDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IExaminationService;

@RestController
@RequestMapping(value = "/api/examinations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExaminationController {

	private IExaminationService examinationService;
	
	@Autowired
	public ExaminationController(IExaminationService examinationService) {
		this.examinationService = examinationService;
	}
	
	@GetMapping(value = "/freeExaminationsAtDermatoloist")
	@PreAuthorize("hasRole('PATIENT')")
	public List<ScheduledExaminationDTO> getFreeExaminationsAtDermatologist() {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return examinationService.getFreeExaminationsAtDermatologist(user.getId());
	}
	
	@PostMapping(value = "/scheduleAtDermatologist")
	@PreAuthorize("hasRole('PATIENT')")
	public String scheduleAtDermatologist(@RequestParam("examinationId") Long examinationId) {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return examinationService.scheduleExamination(user.getId(), examinationId);
	}
	
	@PostMapping(value = "/cancelAppointment")
	@PreAuthorize("hasRole('PATIENT')")
	public void cancelAppointment(@RequestParam("examinationId") Long examinationId) {
		examinationService.cancelAppointment(examinationId);
	}
	
	@GetMapping(value = "/patientsAppointments")
	@PreAuthorize("hasRole('PATIENT')")
	public List<ScheduledExaminationDTO> getPatientsAppointments() {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return examinationService.getPatientsExaminations(user.getId());
	}
	

	@PostMapping(value="/all/scheduled")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public List<ExaminationDTO> getAllScheduledExaminationForHealthWorker(@RequestBody TimeIntervalDTO timeInterval, Authentication auth){
		if(timeInterval.getPharmacyId().equals(""))
			return examinationService.getExaminationsByHealthWorkerIdInTimeInterval(((User) auth.getPrincipal()).getId(), timeInterval.getStart(), timeInterval.getEnd(), ExaminationStatus.SCHEDULED);
		
		return examinationService.getExaminationsByHealthWorkerIdInTimeInterval(((User) auth.getPrincipal()).getId(), timeInterval.getStart(), timeInterval.getEnd(), ExaminationStatus.SCHEDULED,Long.parseLong(timeInterval.getPharmacyId()));
	}
	
	@GetMapping(value="/patient")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public Patient getPatientFromExamination(@RequestParam("examination-id") Long id ) {
		return examinationService.getPatientFromExamination(id);
	}
	
	@PutMapping(value="/not-realized/{id}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public void PassExamination (@PathVariable("id") Long id ) {
		examinationService.punishPatientAndUpdateExaminationStatus(id);		
	}
	
	@PutMapping(value="/information")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public HttpStatus updateInformation(@RequestBody ExaminationInformationDTO examinationInformation) {
		if( examinationService.updateInformation(examinationInformation.getExaminationId(),examinationInformation.getInformation()))
			return HttpStatus.OK;
		return HttpStatus.METHOD_NOT_ALLOWED;
	}

	@PostMapping(value = "/pharmaciesWithFreeAppointments")
	@PreAuthorize("hasRole('PATIENT')")
	public List<PharmacyWithFreeAppointmentDTO> getFreePharmaciesAppointments(@RequestBody Date date) {
		return examinationService.getFreePharmaciesAppointments(date);
	}
	
	@PostMapping(value = "/freePharmacistsInPharmacy")
	@PreAuthorize("hasRole('PATIENT')")
	public List<PharmacistWithFreeAppointmentDTO> getFreePharmaciesAppointments(@RequestBody RequestDTO dto) {
		return examinationService.getFreePharmacistInPharmacy(dto.getPharmacy_id(), dto.getDate());
	}
	
	@PutMapping(value = "/scheduleAtPharmacist")
	@PreAuthorize("hasRole('PATIENT')")
	public String scheduleAtPharmacist(@RequestBody RequestDTO dto) {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return examinationService.scheduleAtPharmacist(user.getId(), dto.getPharmacy_id(), dto.getDate());
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/create")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public String createNewExaminations(@RequestBody NewExaminationsDTO newExaminations, Authentication auth) {
		User user = (User) auth.getPrincipal();
		newExaminations.getDate().setMinutes(0);
		newExaminations.getDate().setHours(1);
		newExaminations.getDate().setSeconds(0);
		return examinationService.createNewExaminations(newExaminations, user.getId());
	}
	
	@GetMapping(value = "/patientsFinishedAppointments")
	@PreAuthorize("hasRole('PATIENT')")
	public List<ScheduledExaminationDTO> getPatientsFinishedAppointments() {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return examinationService.getPatientsFinishedAppointments(user.getId());
	}
	
	@GetMapping(value = "/pharamcy")
	public Pharmacy getPharmacyFromExamination(@RequestParam("examination-id") Long id) {
		return examinationService.getPharmacyByExamination(id);
	}
	
	@PostMapping(value= "/pharmacy/{pid}/available")
	public List<ExaminationDTO> searchAllFreeExaminationsInSpecificDays(@PathVariable("pid")Long pharmacyId,@RequestBody TimeIntervalDTO timeInterval,Authentication auth){
		User user = (User) auth.getPrincipal();
		return examinationService.searchAllFreeExaminationsInSpecificDays(pharmacyId, timeInterval.getStart(), timeInterval.getEnd(), user.getId());
	}
	
	@PostMapping(value="/health-worker/schedule")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public String scheduleExamination(@RequestBody ScheduleNewExanimationDTO schedule){	
		return examinationService.scheduleExamination(schedule.getPatientId(),schedule.getExaminationId());
	}
	
	@PostMapping(value="/finish/{id}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public Boolean finishExamination(@PathVariable("id") Long examinationId){	
		System.out.println("********************"+examinationId);
		return examinationService.finishExamination(examinationId);
	}
}
