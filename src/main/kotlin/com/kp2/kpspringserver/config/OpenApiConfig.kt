package com.kp2.kpspringserver.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = Info(title = "KP API", version = "1.0.0"),
    security = [SecurityRequirement(name = "sessions")])
class OpenApiConfig {

}