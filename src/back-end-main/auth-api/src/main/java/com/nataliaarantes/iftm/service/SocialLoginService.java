package com.nataliaarantes.iftm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nataliaarantes.iftm.model.dto.login.LoginResponseDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterDTO;

@Service
public class SocialLoginService {

    @Autowired private AuthorizationService authorizationService;

    public LoginResponseDTO handleGoogleLogin(RegisterDTO registerDTO) {
        return authorizationService.loginWithGoogle(registerDTO);
    }
}