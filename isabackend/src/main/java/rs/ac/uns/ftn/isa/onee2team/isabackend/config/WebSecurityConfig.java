package rs.ac.uns.ftn.isa.onee2team.isabackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.RestAuthenticationEntryPoint;
import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.TokenAuthenticationFilter;
import rs.ac.uns.ftn.isa.onee2team.isabackend.auth.TokenUtils;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.UserService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				.authorizeRequests().antMatchers("/api/auth/**").permitAll().antMatchers("/h2-console/**").permitAll().antMatchers("/api/").permitAll()
				
				.anyRequest().authenticated().and()
				
				.cors().and()

				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userService),
						BasicAuthenticationFilter.class);
		http.csrf().disable();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js", "/api/users/patients", "/api/pharmacies/all", "/api/pharmacies/pharmaciesWithMedicines");
	}
	
}
