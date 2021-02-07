package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.WorkingCalendar;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestWithHealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.WorkingTimeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IVacationRequestService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IWorkingCalendarService;

@RestController
@RequestMapping(value = "/api/calendar")
public class CalendarController {

	private IVacationRequestService vacationService;
	private IWorkingCalendarService workingCalendarService;

	@Autowired
	public CalendarController(IVacationRequestService vacationService, IWorkingCalendarService workingCalendarService) {
		this.vacationService = vacationService;
		this.workingCalendarService = workingCalendarService;
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
	public List<VacationRequestWithHealthWorkerDTO> getAllVacationRequestsFromPharmacists(
			Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		return vacationService.getAllVacationRequestsFromPharmacists(loggedUser.getId());
	}

	@PutMapping(value = "/decline-request")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('SYSTEM_ADMIN')")
	public Boolean declineVacationRequest(@RequestBody VacationRequestWithHealthWorkerDTO request) {
		return vacationService.declineVacationRequest(request);
	}

	@PutMapping(value = "/accept-request")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')" + "||" + "hasRole('SYSTEM_ADMIN')")
	public Boolean acceptVacationRequest(@RequestBody VacationRequestWithHealthWorkerDTO request,
			Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		return vacationService.acceptVacationRequest(request, loggedUser.getId());
	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/dermatologist")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<WorkingCalendar> getWorkingTimeForDermatologist(@RequestBody WorkingTimeDTO dto,
			Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		dto.getDate().setMinutes(0);
		dto.getDate().setHours(1);
		dto.getDate().setSeconds(0);
		WorkingCalendar ret = workingCalendarService.getWorkingTimeForDermatologist(dto, loggedUser.getId());
		if (ret == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		else if (ret.getId() == -1L) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Bad request", "Selected health worker is on vacation!");
			return new ResponseEntity<>(ret, headers, HttpStatus.BAD_REQUEST);
		} else if (ret.getId() == -2L) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Bad request", "Health worker already have created examinations!");
			return new ResponseEntity<>(ret, headers, HttpStatus.BAD_REQUEST);
		} else if (ret.getId() == -3L) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Bad request", "This dermatologist doesn't work in your pharmacy!");
			return new ResponseEntity<>(ret, headers, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(ret);
	}
}
