package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.Complaint;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IComplaintRepository;

@Service
public class FeedbackService implements IFeedbackService {

	private IComplaintRepository complaintRepository;
	private IEmailNotificationService emailNotificationService;

	@Autowired
	public FeedbackService(IComplaintRepository complaintRepository, IEmailNotificationService emailNotificationService) {
		super();
		this.complaintRepository = complaintRepository;
		this.emailNotificationService = emailNotificationService;
	}

	@Override
	public Complaint saveComplaint(Complaint c) {
		return complaintRepository.save(c);
	}

	@Override
	public Complaint answerComplaint(Long id, String answer) {
		Complaint c = complaintRepository.findById(id).orElse(null);
		if (!c.getHandled()) {
			c.setHandled(true);
			c.setAnswer(answer);
			//does not work if fetch type lazy
			c = this.saveComplaint(c);
			emailNotificationService.sendNotificaitionAsync(c.getPatient().getEmail(), "Answer to complaint number: " + c.getId().toString(), answer);
			return c;
		} else return null;
	}

	@Override
	public List<Complaint> getUnansweredComplaints() {
		return complaintRepository.findAllByHandled(false);
	}
	
	
}
