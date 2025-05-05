package com.nataliaarantes.iftm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iftm.edu.br.Dto.Autenticacao.login.LoginResponseDTO;
import iftm.edu.br.Dto.Autenticacao.register.RegisterDTO;

@Service
public class SocialLoginService {

    @Autowired private AuthorizationService authorizationService;

    public LoginResponseDTO handleGoogleLogin(RegisterDTO registerDTO) {
        return authorizationService.loginWithGoogle(registerDTO);
    }
}