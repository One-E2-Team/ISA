package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Authority;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IAuthorityRepository;

@Service
public class AuthorityService implements IAuthorityService {
	
	private IAuthorityRepository authorityRepository;
	
	@Autowired
	public AuthorityService(IAuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	@Override
	public List<Authority> findById(Long id) {
		Authority auth = authorityRepository.getOne(id);
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(auth);
		return auths;
	}

	@Override
	public List<Authority> findByname(String name) {
		Authority auth = authorityRepository.findByName(name);
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(auth);
		return auths;
	}
	
	

}
