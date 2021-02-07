package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewExaminationsDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacistWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.PharmacyWithFreeAppointmentDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
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
		return examinationService.getFreeExaminationsAtDermatologist();
	}
	
	@PostMapping(value = "/scheduleAtDermatologist")
	@PreAuthorize("hasRole('PATIENT')")
	public void scheduleAtDermatologist(@RequestParam("examinationId") Long examinationId) {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		examinationService.scheduleAtDermatologist(user.getId(), examinationId);
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
	public void scheduleAtPharmacist(@RequestBody RequestDTO dto) {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		examinationService.scheduleAtPharmacist(user.getId(), dto.getPharmacy_id(), dto.getDate());
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
	
}
