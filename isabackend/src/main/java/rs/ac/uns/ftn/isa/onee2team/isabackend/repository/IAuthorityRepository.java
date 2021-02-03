package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Authority;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(String name);
}
