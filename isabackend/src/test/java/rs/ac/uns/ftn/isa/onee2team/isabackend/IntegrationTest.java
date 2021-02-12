package rs.ac.uns.ftn.isa.onee2team.isabackend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.LoyaltyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPromotionService;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = IsabackendApplication.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {

	@Autowired
	IPromotionService promotionService;
	
	@EventListener
	public void seed(ContextRefreshedEvent event) {
	}
	
	@Before
	public void seed() throws Exception {
	}
	
	@Test()
	void allPatientsTest() {
		
		System.out.println("111");
		
		LoyaltyDTO dto = new LoyaltyDTO();
		dto.setType(CategoryType.SILVER);
		dto.setDiscount(0.2);
		dto.setExaminationPoints(3);
		dto.setMinPoints(20);
		
		promotionService.save(dto);
		System.out.println("SAVED!");
		
		assertEquals(1, promotionService.getAllLoyalties().size());
		
		System.out.println("Test done!");
	}
	
	@Test
	void allPatientsTest2() {
		
		System.out.println("222");
		
		LoyaltyDTO dto = new LoyaltyDTO();
		dto.setType(CategoryType.SILVER);
		dto.setDiscount(0.2);
		dto.setExaminationPoints(3);
		dto.setMinPoints(20);
		
		promotionService.save(dto);
		System.out.println("SAVED!");
		
		assertEquals(1, promotionService.getAllLoyalties().size());
		
		System.out.println("Test done!");
	}
}

