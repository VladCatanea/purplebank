package org.purple.spring.mybank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

	   @Bean
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(AbstractHttpConfigurer::disable)
	     .authorizeHttpRequests((authorize) -> authorize
	      .anyRequest().authenticated()
	     )
	     .httpBasic(Customizer.withDefaults())
	     .formLogin(Customizer.withDefaults());

	    return http.build();
	   }

	@Bean
	public UserDetailsService userDetailsService() {
//		@SuppressWarnings("deprecation")
//		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
//				.build();
		UserDetails user = User.withUsername("user").password("{bcrypt}$2a$10$E8.mU2DkqCOwvR87cFh4geJTQehso33IHBbChZ58wNrtBxy7IwfOW").roles("USER").build();
		UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$10$E8.mU2DkqCOwvR87cFh4geJTQehso33IHBbChZ58wNrtBxy7IwfOW").roles("ADMIN").build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}
	
}