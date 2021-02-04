package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import org.springframework.mail.MailException;

public interface IEmailNotificationService {
	void sendNotificaitionAsync(String sendTo, String subject, String mailMessage) throws MailException, InterruptedException;
}
