package rs.ac.uns.ftn.isa.onee2team.isabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class IsabackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsabackendApplication.class, args);
	}

}
