package org.heathtech.tilt.webserver.frameworks.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
//@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    // TODO: Remove this, or make it restricted but permissive
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("POST", "GET", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(false).maxAge(3600)
    }
}