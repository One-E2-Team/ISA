package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Authority;

public interface IAuthorityService {
	List<Authority> findById(Long id);
	List<Authority> findByname(String name);
}
