package rs.ac.uns.ftn.isa.onee2team.isabackend.service;


public interface IEmailNotificationService {
	void sendNotificaitionAsync(String sendTo, String subject, String mailMessage);
}
