package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
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
	
}
