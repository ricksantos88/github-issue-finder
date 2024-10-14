package tech.wendel.swap.github.issue.finder.infrastructure.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("GitHub Issues Finder")
                .description("This is a simple API to find issues and contributors from a GitHub repository")
                .version("1.0.0")
                .termsOfService("00.00.00")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
            );
    }

}
