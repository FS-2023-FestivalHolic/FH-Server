package com.gdsc.festivalholic.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Festival Holic API Document")
                .description("맥주 축제 API 문서")
                .version("1.0.0");
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> b5b8924326e76ad608845dbad68bff3d8b36eee3