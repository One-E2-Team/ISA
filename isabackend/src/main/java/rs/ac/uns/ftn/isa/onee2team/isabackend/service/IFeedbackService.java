package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewComplaintDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.Complaint;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;

public interface IFeedbackService {	
	Complaint saveComplaint(Complaint c);
	Complaint answerComplaint(Long id, String answer);
	List<Complaint> getUnansweredComplaints();
	List<NewComplaintDTO> findPossibleComplaints(Patient patient);
	Complaint createComplaint(Patient patient, NewComplaintDTO ncdto, Long complaintOn);
}
