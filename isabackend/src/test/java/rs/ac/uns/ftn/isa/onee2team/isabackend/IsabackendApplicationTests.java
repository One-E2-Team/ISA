package rs.ac.uns.ftn.isa.onee2team.isabackend;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ERecipeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ERecipeMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.LoyaltyDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IComplaintRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IERecipeRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.ILoyaltyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IFeedbackService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPharmacyService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IPromotionService;

@SpringBootTest
@ContextConfiguration(classes = IsabackendApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class IsabackendApplicationTests {

	@Autowired
	private IFeedbackService feedbackService;
	@Autowired
	private IComplaintRepository complaintRepository;
	@Autowired 
	private IPharmacyService pharmacyService;
	@Autowired
	private IERecipeRepository eRecipeRepository;
	@Autowired
	private IPromotionService promotionService;
	@Autowired
	private ILoyaltyRepository loyaltyRepository;
	
	
	//Student4 - integracioni
	@Test
	@Sql(scripts = {"/complaintdata.sql"})
	void complaintTransactionAndWorkIntegrationTest() throws Throwable {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				feedbackService.answerComplaint(1L, "thread1");
			}
		});
		
		
		Future<?> future1 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        try {
		        	Thread.sleep(2000);
		        } catch (InterruptedException e) {
					e.printStackTrace();
		        }
		        feedbackService.answerComplaint(1L, "thread2");
			}
		});
		executor.awaitTermination(10, TimeUnit.SECONDS);
		assertAll(() -> {
			assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
				try {
				    future1.get();
				} catch (ExecutionException e) {
				    System.out.println("Exception from thread " + e.getCause().getClass());
				    throw e.getCause();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}, () -> {
			assertEquals("thread1", complaintRepository.findById(1L).get().getAnswer());
		});
		executor.shutdown();
	}
	
	//Student4 - integracioni
	@Test
	@Sql(scripts = {"/eRecipeWarehousedata.sql"})
	void eRecipeWarehouseTransactionAndWorkIntegrationTest() throws Throwable {
		ERecipeDTO erdto = new ERecipeDTO();
		erdto.setCode("abc123");
		erdto.setDate(new Date());
		erdto.setPatientId(3L);
		ArrayList<ERecipeMedicine> list = new ArrayList<ERecipeMedicine>();
		list.add(new ERecipeMedicine(1L, "Letrox", 10));
		list.add(new ERecipeMedicine(2L, "FLOBIAN", 2));
		erdto.setMedicine(list);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				pharmacyService.buyByERecipe(1L, erdto, 3L);
			}
		});
		
		
		Future<?> future1 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        try {
		        	Thread.sleep(2000);
		        } catch (InterruptedException e) {
					e.printStackTrace();
		        }
		        erdto.setCode("abc124");
		        pharmacyService.buyByERecipe(1L, erdto, 3L);
			}
		});
		executor.awaitTermination(10, TimeUnit.SECONDS);
		assertAll(() -> {
			assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
				try {
				    future1.get();
				} catch (ExecutionException e) {
				    System.out.println("Exception from thread " + e.getCause().getClass());
				    throw e.getCause();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}, () -> {
			assertEquals(1, eRecipeRepository.findAll().size());
		}, () -> {
			assertEquals("abc123", eRecipeRepository.findAll().get(0).getCode());
		});
		executor.shutdown();
	}
	
	//Student4 - integracioni
		@Test
		@Sql(scripts = {"/loyaltydata.sql"})
		void loyaltyTransactionAndWorkIntegrationTest() throws Throwable {
			LoyaltyDTO ldto = new LoyaltyDTO();
			ldto.setDiscount(1.0);
			ldto.setMinPoints(12);
			ldto.setExaminationPoints(1);
			ldto.setType(CategoryType.GOLD);
			ExecutorService executor = Executors.newFixedThreadPool(2);
			executor.submit(new Runnable() {
				
				@Override
				public void run() {
					promotionService.save(ldto);
				}
			});
			
			
			Future<?> future1 = executor.submit(new Runnable() {
				
				@Override
				public void run() {
			        try {
			        	Thread.sleep(2000);
			        } catch (InterruptedException e) {
						e.printStackTrace();
			        }
			        ldto.setDiscount(1.0);
			        promotionService.save(ldto);
				}
			});
			executor.awaitTermination(10, TimeUnit.SECONDS);
			assertAll(() -> {
				assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
					try {
					    future1.get();
					} catch (ExecutionException e) {
					    System.out.println("Exception from thread " + e.getCause().getClass());
					    throw e.getCause();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
			}, () -> {
				assertEquals(1.0, loyaltyRepository.findById(3L).get().getDiscount());
			});
			executor.shutdown();
		}
		
		

}