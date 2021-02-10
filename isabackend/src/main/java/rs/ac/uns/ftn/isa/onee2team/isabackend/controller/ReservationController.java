package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestReservationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ReservedMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IMedicineReservationService;

@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {

	private IMedicineReservationService medicineReservationService;
	
	@Autowired
	public ReservationController(IMedicineReservationService medicineReservationService) {
		this.medicineReservationService = medicineReservationService;
	}
	
	@GetMapping(value = "/patientsReservations")
	@PreAuthorize("hasRole('PATIENT')")
	public List<ReservedMedicineDTO> getPatientsReservations(){
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return medicineReservationService.getPatientsReservations(user.getId());
	}
	
	@PutMapping(value = "/cancelReservation")
	@PreAuthorize("hasRole('PATIENT')")
	public boolean cancelReservation(@RequestParam("reservation_id") Long reservation_id){
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return medicineReservationService.cancelReservation(user.getId(), reservation_id);
	}
	
	@PostMapping(value = "/reserve")
	@PreAuthorize("hasRole('PATIENT')")
	public boolean reserve(@RequestBody RequestReservationDTO dto){
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return medicineReservationService.reserve(dto, user.getId());
	}
	
	@PutMapping(value = "/takeMedicine")
	@PreAuthorize("hasRole('PATIENT')")
	public void takeMedicine(@RequestBody ReservedMedicineDTO dto){
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		medicineReservationService.takeMedicine(user.getId(), dto);
	}
}
