package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.StringInformationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.Complaint;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IFeedbackService;

@RestController
@RequestMapping(value = "api/feedback")
public class FeedbackController {

	private IFeedbackService feedbackService;

	@Autowired
	public FeedbackController(IFeedbackService feedbackService) {
		super();
		this.feedbackService = feedbackService;
	}
	
	@PostMapping(value = "/answerComplaint/{id}")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public Complaint answerComplaint(@PathVariable("id") Long id, @RequestBody StringInformationDTO sidto) {
		return feedbackService.answerComplaint(id, sidto.getInfo());
	}
}
