package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.Complaint;

public interface IFeedbackService {	
	Complaint saveComplaint(Complaint c);
	Complaint answerComplaint(Long id, String answer);
}