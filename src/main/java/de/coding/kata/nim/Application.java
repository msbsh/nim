package de.coding.kata.nim;

import de.coding.kata.nim.rest.GameEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@SpringBootApplication
public class Application {

    @Value("${application.version:0.0.0}")
    private String version;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket commandApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Game")
				.select()
				.apis(RequestHandlerSelectors.basePackage(GameEndpoint.class.getPackage().getName()))
				.paths(PathSelectors.ant("/api/**") )
				.build()
				.apiInfo(new ApiInfo(
						"Nim Game API",
						"The nim game API.",
						version,
						null,
						null,
						null,
						null,
						new ArrayList<>()));
	}
}
