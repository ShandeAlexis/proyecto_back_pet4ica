package com.sistema.adopcionmascotas.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/fotos/**") // Ruta URL para acceder a las fotos
                .addResourceLocations(uploadDir); // Ruta en tu sistema de archivos donde se encuentran las fotos
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
