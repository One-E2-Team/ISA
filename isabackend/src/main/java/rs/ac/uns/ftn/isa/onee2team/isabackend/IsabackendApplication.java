package rs.ac.uns.ftn.isa.onee2team.isabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
@EnableAsync
public class IsabackendApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", System.getenv("PORT") != null ? System.getenv("PORT") : System.getenv("ISA_SPRING_PORT"));
		SpringApplication.run(IsabackendApplication.class, args);
	}

}
