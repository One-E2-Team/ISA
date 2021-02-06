package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestWithHealthWorkerDTO;
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
	public void createVacationRequest(@RequestBody VacationRequestDTO request, Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		request.setHealthWorkerId(loggedUser.getId());
		vacationService.save(request);
	}

	@GetMapping(value = "/dermatologists-requests")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public List<VacationRequestWithHealthWorkerDTO> getAllVacationRequestsFromDermatologists() {
		return vacationService.getAllVacationRequestsFromDermatologists();
	}
	
	@GetMapping(value = "/pharmacists-requests")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<VacationRequestWithHealthWorkerDTO> getAllVacationRequestsFromPharmacists(Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		return vacationService.getAllVacationRequestsFromPharmacists(loggedUser.getId());
	}
	
	@PutMapping(value = "/decline")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('SYSTEM_ADMIN')")
	public Boolean declineVacationRequest(@RequestBody VacationRequestWithHealthWorkerDTO request) {
		return vacationService.declineVacationRequest(request);
	}

}
