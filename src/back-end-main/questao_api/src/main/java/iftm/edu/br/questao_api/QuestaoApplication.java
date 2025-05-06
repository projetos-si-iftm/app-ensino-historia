package iftm.edu.br.questao_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class QuestaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestaoApplication.class, args);
	}

    // Configuração global de CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir todas as rotas
                    .allowedOrigins("*") // Permitir qualquer origem
                    .allowedMethods("*") // Permitir todos os métodos HTTP
                    .allowedHeaders("*"); // Permitir todos os cabeçalhos
            }
        };
    }
}
