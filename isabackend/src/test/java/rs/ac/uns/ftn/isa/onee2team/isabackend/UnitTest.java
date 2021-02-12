package rs.ac.uns.ftn.isa.onee2team.isabackend;

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
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPromotionRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPromotionService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.PromotionService;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UnitTest {

	
	
	@Mock
	private IPromotionRepository promotionRepository;
	
	@Mock
	private Promotion promotion;
	
	@InjectMocks
	private PromotionService promotionService;
	
	@Before
	public void SetUp() {
		
	}
	
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
		
		assertThat(p).hasSize(1);
		
	}
}
