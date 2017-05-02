package com.basaki.example.menagerie.swagger.plugin;

import com.basaki.example.menagerie.swagger.annotation.ApiOperationSince;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

/**
 * {@code SinceOperationBuilder} swagger plugin to convert {@code
 * ApiOperationSince} annotation to a swagger json object.
 * <p>
 *
 * @author Indra Basak
 * @since 5/1/17
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1002)
@Slf4j
public class SinceOperationBuilder implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        Optional<ApiOperation>
                annotation = context.findAnnotation(ApiOperation.class);

        Optional<ApiOperationSince> apiSinceOperation =
                context.findAnnotation(ApiOperationSince.class);
        if (apiSinceOperation.isPresent() && apiSinceOperation.get().value() != null) {
            String value = apiSinceOperation.get().value();
            String description = apiSinceOperation.get().description();
            ObjectVendorExtension ext = new ObjectVendorExtension("x-since");
            if (description != null) {
                ext.addProperty(
                        new StringVendorExtension("description", description));
            }
            ext.addProperty(new StringVendorExtension("value", value));

            context.operationBuilder().extensions(
                    Collections.singletonList(ext));
            log.debug("Added since ", value);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
