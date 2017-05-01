package com.basaki.example.menagerie.swagger.plugin;

import com.basaki.example.menagerie.swagger.annotation.ApiOperationSince;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

/**
 * Created by indra.basak on 5/1/17.
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1002)
@Slf4j
public class SinceOperationBuilderPlugin implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        Optional<ApiOperation>
                annotation = context.findAnnotation(ApiOperation.class);

        Optional<ApiOperationSince> apiSinceOperation =
                context.findAnnotation(ApiOperationSince.class);
        if (apiSinceOperation.isPresent() && apiSinceOperation.get().value() != null) {
            String since = apiSinceOperation.get().value();
            context.operationBuilder().extensions(
                    Collections.singletonList(
                            new StringVendorExtension("x-since", since)));
            log.debug("Added since ", since);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
