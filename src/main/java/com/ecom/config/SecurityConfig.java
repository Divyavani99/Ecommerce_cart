package com.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomUserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	
		http.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
		.authorizeHttpRequests(req->req.requestMatchers("/","/signin", "/register","/**","/api/**").permitAll().
				anyRequest().authenticated())
		.formLogin(form->form
				.loginPage("/signin")
				.loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password") 
				.defaultSuccessUrl("/",true)
				.failureHandler(authenticationFailureHandler())
				.successHandler(authenticationSuccessHandler()))
		.logout(logout->logout
				.logoutUrl("/logout")  // The URL to trigger logout
	            .logoutSuccessUrl("/signin?logout=true")  // Redirect to the signin page after logout
	            .invalidateHttpSession(true)  // Invalidate the session
	            .clearAuthentication(true)  // Clear authentication
	            .deleteCookies("JSESSIONID")
				.permitAll());
        return http.build();
    }
    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
    	return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
