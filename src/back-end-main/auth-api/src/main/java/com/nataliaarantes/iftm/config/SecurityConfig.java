package com.nataliaarantes.iftm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataliaarantes.iftm.model.dto.login.LoginResponseDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterDTO;
import jakarta.servlet.http.HttpServletResponse;

import com.nataliaarantes.iftm.service.AuthorizationService;
import com.nataliaarantes.iftm.service.SocialLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  SecurityFilter securityFilter;

  @Lazy
  @Autowired
  AuthorizationService authorizationService;

  @Autowired
   @Lazy
  private SocialLoginService socialLoginService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/v3/api-docs/swagger-config").permitAll()
            .requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
            .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/google").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            .requestMatchers(HttpMethod.GET, "/oauth2/authorization/google").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/validateToken").permitAll()
            .requestMatchers(HttpMethod.POST, "/classroom").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/classroom").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/classroom").hasRole("ADMIN")

            .anyRequest().authenticated())
        .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
            .successHandler(((request, response, authentication) -> {
              Map<String, Object> attributes = ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes();
              String email = (String) attributes.get("email");
              String name = (String) attributes.get("name");

              RegisterDTO registerDTO = new RegisterDTO(name, email, null, false, null);
              LoginResponseDTO loginResponseDTO = socialLoginService.handleGoogleLogin(registerDTO);

              try {
                response.setContentType("application/json");
                response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponseDTO));
                response.getWriter().flush();
              } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar resposta");
              }

            })))
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .accessDeniedHandler(new CustomAccessDeniedHandler()))
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}