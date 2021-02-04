package rs.ac.uns.ftn.isa.onee2team.isabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
@EnableAsync
public class IsabackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsabackendApplication.class, args);
	}

}
