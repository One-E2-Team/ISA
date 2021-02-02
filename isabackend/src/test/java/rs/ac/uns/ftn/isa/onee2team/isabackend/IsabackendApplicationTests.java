package rs.ac.uns.ftn.isa.onee2team.isabackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
  IsabackendApplication.class, 
  H2JpaConfig.class})
@ActiveProfiles("test")
class IsabackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
