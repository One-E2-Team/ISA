package rs.ac.uns.ftn.isa.onee2team.isabackend.service;


public interface IEmailNotificationService {
	void sendNotificationAsync(String sendTo, String subject, String mailMessage);
}
