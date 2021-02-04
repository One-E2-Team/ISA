package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IMedicineService;

@RestController
@RequestMapping(value = "/api/medicines")
public class MedicineController {

	private IMedicineService medicineService;

	@Autowired
	public MedicineController(IMedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<Medicine> getAll() {
		return medicineService.getAll();
	}
}
