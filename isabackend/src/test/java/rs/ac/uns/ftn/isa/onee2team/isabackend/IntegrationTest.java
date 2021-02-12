package rs.ac.uns.ftn.isa.onee2team.isabackend;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;


import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.LoyaltyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPromotionService;

@ContextConfiguration(classes = IsabackendApplication.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {

	@Autowired
	IPromotionService promotionService;
	
	@Test()
	void allPatientsTest() {
		
		/*
		System.out.println("111");
		
		LoyaltyDTO dto = new LoyaltyDTO();
		dto.setType(CategoryType.SILVER);
		dto.setDiscount(0.2);
		dto.setExaminationPoints(3);
		dto.setMinPoints(20);
		
		promotionService.save(dto);*/
	}
	
	@Test
	void allPatientsTest2() {
		
		/*
		System.out.println("222");
		
		LoyaltyDTO dto = new LoyaltyDTO();
		dto.setType(CategoryType.SILVER);
		dto.setDiscount(0.2);
		dto.setExaminationPoints(3);
		dto.setMinPoints(20);
		
		promotionService.save(dto);*/
	}
}

