package com.basaki.example.menagerie.swagger.plugin;

import com.fasterxml.classmate.TypeResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

/**
 * {@code ExampleApiListingScanner} swagger plugin to build a new elephant
 * endpoint.
 * <p></p>
 * Doesn't work with springfox 2.6.1.
 * NOTE: @see <a href="https://github.com/springfox/springfox/issues/1767">#1767</a>
 * <p>
 *
 * @author Indra Basak
 * @since 5/1/17
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1003)
@Slf4j
public class ExampleApiListingScanner implements ApiListingScannerPlugin {

    @Override
    public List<ApiDescription> apply(DocumentationContext context) {
        List<ApiDescription> descps = new ArrayList<>();
        descps.add(createApiDescription());

        return descps;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        //return DocumentationType.SWAGGER_2.equals(documentationType);
        return true;
    }

    private ApiDescription createApiDescription() {
        ApiDescription desc = new ApiDescription(
                "/elephants/children",
                "This is an AP Listing Scanner example ",
                Arrays.asList(createOperation()),
                false);

        return desc;
    }

    private Operation createOperation() {
        ParameterBuilder paramBldr = new ParameterBuilder();
        paramBldr.description("search by child's name")
                .type(new TypeResolver().resolve(String.class))
                .name("name")
                .parameterType("query")
                .parameterAccess("access")
                .required(true)
                .modelRef(new ModelRef("string"));
        Parameter param = paramBldr.build();

        OperationBuilder bldr =
                new OperationBuilder(new CachingOperationNameGenerator());
        bldr.authorizations(new ArrayList())
                .codegenMethodNameStem("readChildrenGET")
                .method(HttpMethod.GET)
                .notes("This is a dynamic method to fetch elephant children")
                .parameters(Arrays.asList(param));

        Operation op = bldr.build();

        return op;
    }
}
