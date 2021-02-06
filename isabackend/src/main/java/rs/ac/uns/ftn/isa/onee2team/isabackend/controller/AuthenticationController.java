package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.ElevatedFirstLogIn;
import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.ResourceConflictException;
import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.TokenUtils;
import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.UserTokenState;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.UserRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IEmailNotificationService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.UserService;


@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IEmailNotificationService emailNotificationService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) {

		User u = userService.findByEmail(authenticationRequest.getEmail());
		if(u != null && passwordEncoder.matches(authenticationRequest.getPassword(), u.getPassword()) && u.getUserType()!=UserType.PATIENT && !u.isEnabled())
			return ResponseEntity.ok(new UserTokenState(null, 0L, u.getUserType(), u.getEmail()));
		// 
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
						authenticationRequest.getPassword()));

		// Ubaci korisnika u trenutni security kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getEmail());
		long expiresIn = tokenUtils.getExpiredIn();
		UserType userType = user.getUserType();
		String email = user.getEmail();

		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, userType, email));
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/register")
	public ResponseEntity<User> addUser(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {

		User existUser = this.userService.findByEmail(userRequest.getEmail());
		if (existUser != null) {
			throw new ResourceConflictException(0L/*userRequest.getEmail()*/, "Email already exists");
		}
		User user = this.userService.createPatient(userRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
		this.emailNotificationService.sendNotificationAsync(user.getEmail(), "Account Validation", "Visit this link and validate your account: http://localhost:8083/api/auth/validate/" + user.getId() + "/");
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/validate/{id}")
	public String validatePatient(@PathVariable("id") Long id) {
		User p = this.userService.findById(id);
		if(p == null)
			return "Bad Request!";
		p.setEnabled(true);
		this.userService.saveUser(p);
		
		return "Validation succesfull, you can use your account now.";
	}
	
	@PostMapping("/login/elevated")
	public ResponseEntity<UserTokenState> createElevatedAuthenticationToken(@RequestBody ElevatedFirstLogIn authenticationRequest,
			HttpServletResponse response){
		User u = userService.findByEmail(authenticationRequest.getEmail());
		if(!passwordEncoder.matches(authenticationRequest.getPassword(), u.getPassword()))
			return ResponseEntity.ok(new UserTokenState(null, 0L, u.getUserType(), u.getEmail()));
		u.setPassword(passwordEncoder.encode(authenticationRequest.getNewPassword()));
		u.setEnabled(true);
		this.userService.saveUserAndFlush(u);
		authenticationRequest.setPassword(authenticationRequest.getNewPassword());
		return createAuthenticationToken(authenticationRequest, response);
	}
}
