package com.sistema.adopcionmascotas.configuracion;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/fotos/**") // Ruta URL para acceder a las fotos
                .addResourceLocations("file:C:/Users/Alexis/Downloads/mini-sistema-blog-api-rest-spring-master/sistema-blog-spring-boot-api-rest/fotos/"); // Ruta en tu sistema de archivos donde se encuentran las fotos
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","https://qfrj5skv-5173.brs.devtunnels.ms") // Agrega el origen permitido
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowCredentials(true) // Permitir credenciales (cookies, encabezados de autorización, etc.)
                .maxAge(3600); // Tiempo máximo de caché de pre-vuelo
    }
}
