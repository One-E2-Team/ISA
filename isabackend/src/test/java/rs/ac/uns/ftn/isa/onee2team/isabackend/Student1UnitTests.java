package rs.ac.uns.ftn.isa.onee2team.isabackend;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Loyalty;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.ILoyaltyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPromotionRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPharmacyService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.MedicineService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.PharmacyService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.PromotionService;

import static org.mockito.Mockito.when;

@SpringBootTest
class Student1UnitTests {
	
	@Mock
	private IPromotionRepository promotionRepository;
	
	@Mock
	private IPharmacyRepository pharmacyRepository;
	
	@Mock
	private IMedicineRepository medicineRepository;
	
	@Mock
	ILoyaltyRepository loyaltyRepository;
	
	@InjectMocks
	private PromotionService promotionService;
	
	@InjectMocks
	private PharmacyService pharmacyService;
	
	@InjectMocks
	private MedicineService medicineService;
	
	@Test
	void promotionServiceFindAllTest() {
		
		Promotion promotion = new Promotion();
		promotion.setId(1L);
		promotion.setDescription("Some description");
		promotion.setStartDate(new Date());
		promotion.setEndDate(new Date());
		promotion.setPharmacy(null);
		
		when(promotionRepository.findAll()).thenReturn(Arrays.asList(promotion));
		
		List<Promotion> p = promotionRepository.findAll();
		
		assertEquals(1, p.size());
	}
	
	@Test
	void pharmacyServiceFindOneEqualsPharmacyNameTest() {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1L);
		pharmacy.setName("Apoteka1");
		pharmacy.setAddress("Adresa1");
		pharmacy.setDescription("Opis1");
		pharmacy.setLatitude(15.0);
		pharmacy.setLongitude(15.0);
		pharmacy.setSubscribedPatients(null);
		
		when(pharmacyService.findAll()).thenReturn(Arrays.asList(pharmacy));
		
		Pharmacy testPharmacy = pharmacyService.findAll().get(0);
		
		assertEquals("Apoteka1", testPharmacy.getName());
	}
	
	@Test 
	void test() {
		Loyalty loyalty = new Loyalty();
		loyalty.setId(1L);
		loyalty.setDiscount(0.2);
		loyalty.setMinPoints(20);
		loyalty.setType(CategoryType.SILVER);
		loyalty.setExaminationPoints(5);
		
		when(loyaltyRepository.findAll()).thenReturn(Arrays.asList(loyalty));
		
		List<Loyalty> loyaltiesTest = promotionService.getAllLoyalties();
		
		assertEquals(1, loyaltiesTest.size());
	}
	
	@Test
	void test2() {
		Medicine m = new Medicine();
		m.setId(1L);
		m.setCode(123L);
		m.setContexture("Nesto");
		m.setDailyIntake(2);
		m.setManufacturer("Proizvodjac");
		m.setMedicineForm("Forma");
		m.setMedicineType("Tip");
		m.setName("Lek1");
		m.setPoints(5);
		m.setRecipeNeeded(false);
		m.setSideEffects("Efekti");
		when(medicineRepository.findMedicineByPharmacyid(1L)).thenReturn((Arrays.asList(m)));
		
		List<Medicine> lista = medicineRepository.findMedicineByPharmacyid(1L);
		//asssertEquals("Proizvodjac", lista.get(0).getManufacturer());
	}
}
