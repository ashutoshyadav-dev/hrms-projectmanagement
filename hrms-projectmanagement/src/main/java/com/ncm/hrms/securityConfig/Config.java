package com.ncm.hrms.securityConfig;

import com.ncm.hrms.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Config {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        
    	http.cors(cors -> {})
    	    .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/admin/**")
                   .hasAuthority("ROLE_ADMIN")
                .requestMatchers("/leaves" , "/leaves/**")
                   .hasAnyAuthority("ROLE_ADMIN","ROLE_EMPLOYEE")
                .requestMatchers("/projects/**","/technologies/**")
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .requestMatchers("/employee/all","/assignProjectToEmp")
                    .hasAuthority("ROLE_ADMIN")
                .requestMatchers("/employee/**","/designations","/designations/**")
                    .hasAnyAuthority("ROLE_EMPLOYEE","ROLE_ADMIN")
                .requestMatchers("/assignProjectToEmp/byEmail")
                    .hasAuthority("ROLE_EMPLOYEE")
                .requestMatchers("/api/attendance/**")
                   .hasAuthority("ROLE_EMPLOYEE")
                .requestMatchers("/api/shifts","/api/shifts/**")
                   .hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/documents/**")
                   .hasAnyAuthority("ROLE_EMPLOYEE","ROLE_ADMIN")
                .anyRequest().authenticated()
            );
    	
//    	http.formLogin(login->login.permitAll());
	

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
