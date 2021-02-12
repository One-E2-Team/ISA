package rs.ac.uns.ftn.isa.onee2team.isabackend;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IComplaintRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IFeedbackService;

@SpringBootTest
@ContextConfiguration(classes = IsabackendApplication.class)
class IsabackendApplicationTests {

	@Autowired
	private IFeedbackService feedbackService;
	@Autowired
	private IComplaintRepository complaintRepository;
	
	
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
		executor.shutdown();
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
		
	}

}