package com.nataliaarantes.iftm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nataliaarantes.iftm.model.Student;
import com.nataliaarantes.iftm.model.Teacher;
import com.nataliaarantes.iftm.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      var userInstance = user.getClass();
      String type;
      String classId = "";
      if(userInstance == Teacher.class) {
        type = "teacher";
      } else {
        type = "student";
        classId = ((Student) user).getClassId();
      }

      String token = JWT.create()
          .withIssuer("auth-api")
          .withSubject(user.getEmail())
          .withClaim("uuid", user.getUuid())
          .withClaim("type", type)
          .withClaim("classId", classId)
          .withExpiresAt(genExpirationDate())
          .sign(algorithm);
      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public String validateToken(String token){
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("auth-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException exception){
      return "";
    }
  }

  private Instant genExpirationDate(){
    return LocalDateTime.now().plusYears(1).toInstant(ZoneOffset.of("-03:00"));
  }
}
