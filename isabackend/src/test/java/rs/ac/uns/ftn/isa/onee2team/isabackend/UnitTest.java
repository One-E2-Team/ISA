package rs.ac.uns.ftn.isa.onee2team.isabackend;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPromotionRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.PromotionService;

import static org.mockito.Mockito.when;

@SpringBootTest
class UnitTest {
	
	@Mock
	private IPromotionRepository promotionRepository;
	
	@Mock
	private Promotion promotion;
	
	@InjectMocks
	private PromotionService promotionService;
	
	@Test
	void contextLoads() {
		
		
		promotion = new Promotion();
		promotion.setId(1L);
		promotion.setDescription("Some description");
		promotion.setStartDate(new Date());
		promotion.setEndDate(new Date());
		promotion.setPharmacy(null);
		
		when(promotionRepository.findAll()).thenReturn(Arrays.asList(promotion));
		
		List<Promotion> p = promotionRepository.findAll();
		
		assertEquals(1, p.size());
		
	}
}
