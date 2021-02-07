package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.TimeIntervalDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IExaminationService;

@RestController
@RequestMapping(value = "api/examination")
public class ExaminaionController {

	private IExaminationService examinationService;
	
	@Autowired
	public ExaminaionController(IExaminationService examinationService) {
		this.examinationService = examinationService;
	}
	
	@PostMapping(value="/all/scheduled")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')" + "||" + "hasRole('ROLE_PHARMACIST')")
	public List<ExaminationDTO> getAllScheduledExaminationForHealthWorker(@RequestBody TimeIntervalDTO timeInterval, Authentication auth){
		if(timeInterval.getPharmacyId().equals(""))
			return examinationService.getExaminationsByHealthWorkerIdInTimeInterval(((User) auth.getPrincipal()).getId(), timeInterval.getStart(), timeInterval.getEnd(), ExaminationStatus.SCHEDULED);
		
		return examinationService.getExaminationsByHealthWorkerIdInTimeInterval(((User) auth.getPrincipal()).getId(), timeInterval.getStart(), timeInterval.getEnd(), ExaminationStatus.SCHEDULED,Long.parseLong(timeInterval.getPharmacyId()));
	}
	
}
