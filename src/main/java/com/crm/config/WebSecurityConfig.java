package com.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.crm.service.UserDetailsServiceImpl;
import com.crm.util.AuthTokenFilter;

@Configuration
@EnableWebSecurity //Per abilitare la Secirity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService; //Gestire i dati dell'utente (Injection)

	@Bean 
	public PasswordEncoder passwordEncoder() { //Serve a criptare le password
		return new BCryptPasswordEncoder();
	}

	@Bean 
	public AuthTokenFilter authenticationJwtTokenFilter() { //Applicare un filtro alle richeiste dove serve una autenticazione
		return new AuthTokenFilter();
	}

	@Override
	//Metodo per gestire gli utenti 
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
		.userDetailsService(userDetailsService)
		.passwordEncoder(this.passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
         http.cors()
         .and()
         .headers()
         .frameOptions()
         .sameOrigin()
         .and()
         .csrf()
         .disable()
         .authorizeRequests()
         .antMatchers("/auth/**","/web/**").permitAll()
          .antMatchers("/h2-console/**").permitAll()
          .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/configuration/**", "/swagger-resources/**", "/webjars/**").permitAll()
          .anyRequest().authenticated()
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
        /**
         * per far funzionare h2 console
         */
        http.headers().frameOptions().disable();
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	//antMatchers - Http autorizza, se la rotta combacia con l'admin puoi accedere solo se sei Admin. 
	// Alla fine ti rimanda al formLogin.
	

}
