package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationAtDermatologistDTO;
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
	public List<ExaminationAtDermatologistDTO> getFreeExaminationsAtDermatologist() {
		return examinationService.getFreeExaminationsAtDermatologist();
	}
	
}
