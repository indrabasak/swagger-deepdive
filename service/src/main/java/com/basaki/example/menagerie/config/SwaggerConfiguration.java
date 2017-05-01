package com.basaki.example.menagerie.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.readers.operation.SwaggerOperationResponseClassReader;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * {@code SwaggerConfiguration} is the configuration for setting up swagger for
 * the author controller. The swagger documentation can be viewed at {@code
 * http://<host>:<port>/swagger-ui-html}
 * <p/>
 *
 * @author Indra Basak
 * @since 2/23/17
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Creates the Swagger configuration bean.
     *
     * @return docket bean
     */
    @Bean
    public Docket animalsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("animal")
                .apiDescriptionOrdering(new Ordering<ApiDescription>() {
                    @Override
                    public int compare(ApiDescription left,
                            ApiDescription right) {
                        int leftPos =
                                left.getOperations().size() == 1 ? left.getOperations().get(
                                        0).getPosition() : 0;
                        int rightPos =
                                right.getOperations().size() == 1 ? right.getOperations().get(
                                        0).getPosition() : 0;

                        int position = Integer.compare(leftPos, rightPos);

                        if (position == 0) {
                            position =
                                    left.getPath().compareTo(right.getPath());
                        }

                        return position;
                    }
                })
                .select()
                .apis(exactPackage(
                        "com.basaki.example.menagerie.controller.animal"))

                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("Animal APIs",
                        "Exploring Swagger UI Features"));
    }

    @Bean
    public Docket birdsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("bird")
                .select()
                .apis(exactPackage(
                        "com.basaki.example.menagerie.controller.bird"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("Bird APIs",
                        "Exploring Swagger UI Features"));
    }

    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration(
                null,
                "none",
                //"alpha",
                "function(e, t) { console.log(e.name); return e.name.localeCompare(t.name);}",
                "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                true,
                true,
                60000L);
    }

    /**
     * Creates an object containing API information including author name,
     * email, version, license, etc.
     *
     * @param title       API title
     * @param description API description
     * @return API information
     */
    private ApiInfo apiInfo(String title, String description) {
        Contact contact = new Contact("Indra Basak", "",
                "developer@gmail.com");
        return new ApiInfo(title, description, "1.0", "terms of controller url",
                contact, "license", "license url");
    }

    private static Predicate<RequestHandler> exactPackage(final String pkg) {
        return input -> input.declaringClass().getPackage().getName().equals(
                pkg);
    }
}
