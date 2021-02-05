package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestDTO;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;

import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IVacationRequestService;

@RestController
@RequestMapping(value = "/api/calendar")
public class CalendarController {
	
	private IVacationRequestService vacationService;

	@Autowired
	public CalendarController(IVacationRequestService vacationService) {
		this.vacationService = vacationService;
	}

	@PostMapping(value = "/vacation")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public void createVacationRequest(@RequestBody VacationRequestDTO request,Authentication authentication) {		
		User loggedUser = (User) authentication.getPrincipal(); 
		request.setHealthWorkerId(loggedUser.getId());
		vacationService.save(request);
	}
	
}
