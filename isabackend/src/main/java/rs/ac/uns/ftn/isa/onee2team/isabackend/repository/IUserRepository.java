package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
	@Query(value = "select u from User u where u.userType = 0")
	public List<Patient> getAllPatients();
}
