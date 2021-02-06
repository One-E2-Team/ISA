package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ComplaintDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.StringInformationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.Complaint;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.ComplaintType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IFeedbackService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPharmacyService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IUserService;

@RestController
@RequestMapping(value = "api/feedback")
public class FeedbackController {

	private IFeedbackService feedbackService;
	private IUserService userService;
	private IPharmacyService pharmacyService;

	@Autowired
	public FeedbackController(IFeedbackService feedbackService, IUserService userService, IPharmacyService pharmacyService) {
		super();
		this.feedbackService = feedbackService;
		this.userService = userService;
		this.pharmacyService = pharmacyService;
	}
	
	@PostMapping(value = "/answerComplaint/{id}")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public Complaint answerComplaint(@PathVariable("id") Long id, @RequestBody StringInformationDTO sidto) {
		return feedbackService.answerComplaint(id, sidto.getInfo());
	}
	
	@GetMapping(value = "/unansweredComplaints")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public List<ComplaintDTO> getUnanswerComplaints() {
		List<ComplaintDTO> ret = new ArrayList<ComplaintDTO>();
		for (Complaint c : feedbackService.getUnansweredComplaints()) {
			ComplaintDTO cdto = new ComplaintDTO();
			cdto.setId(c.getId());
			cdto.setPatientName(c.getPatient().getFirstName() + " " + c.getPatient().getLastName());
			cdto.setComment(c.getComment());
			if(c.getType()==ComplaintType.PHARMACY) cdto.setAddressedOn(pharmacyService.getById(c.getComplaintEntityId()).getName());
			else cdto.setAddressedOn(userService.findById(c.getComplaintEntityId()).getFirstName() + " " + userService.findById(c.getComplaintEntityId()).getLastName());
			ret.add(cdto);
		}
		return ret;
	}
}
