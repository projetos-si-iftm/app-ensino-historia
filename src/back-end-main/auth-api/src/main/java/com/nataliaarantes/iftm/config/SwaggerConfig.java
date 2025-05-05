package com.nataliaarantes.iftm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Documentação API AUTH")
            .version("1.0")
            .description("Documentação da API")
            .contact(new Contact()
                .name("Natalia Arantes")
                .email("natalia.arantes@estudante.iftm.edu.br"))
           );
  }
}