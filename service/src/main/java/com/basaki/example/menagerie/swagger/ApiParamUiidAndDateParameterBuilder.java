package com.basaki.example.menagerie.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiParam;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Collections;
import springfox.documentation.schema.Enums;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.schema.ApiModelProperties;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static springfox.documentation.swagger.common.SwaggerPluginSupport.pluginDoesApply;

/**
 * {@code ApiParamUiidAndDateParameterBuilder} swagger plugin to convert default
 * value field to a dynamic field to display newly generated UUID and date in
 * ISO format.
 * <p/>
 *
 * @author Indra Basak
 * @since 4/17/17
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
public class ApiParamUiidAndDateParameterBuilder implements ParameterBuilderPlugin {

    public static final String TYPE_ISO_DATE_TIME = "ISO_DATE_TIME";

    public static final String TYPE_UUID = "UUID";

    @Override
    public boolean supports(DocumentationType delimiter) {
        return pluginDoesApply(delimiter);
    }

    public void apply(ParameterContext context) {
        Optional<ApiParam> apiParam =
                context.resolvedMethodParameter().findAnnotation(
                        ApiParam.class);
        context.parameterBuilder()
                .allowableValues(allowableValues(
                        context.resolvedMethodParameter().getParameterType(),
                        apiParam.transform(toAllowableValue()).or("")));
        if (apiParam.isPresent() && apiParam.get().defaultValue() != null &&
                (apiParam.get().defaultValue().equals(TYPE_ISO_DATE_TIME) ||
                        apiParam.get().defaultValue().equals(TYPE_UUID))) {
            context.parameterBuilder().name(emptyToNull(apiParam.get().name()));
            context.parameterBuilder().description(
                    emptyToNull(apiParam.get().value()));
            context.parameterBuilder().parameterAccess(
                    emptyToNull(apiParam.get().access()));

            String defaultValue = apiParam.get().defaultValue();
            if (TYPE_UUID.equals(defaultValue)) {
                defaultValue = UUID.randomUUID().toString();
            } else if (TYPE_ISO_DATE_TIME.equals(defaultValue)) {
                defaultValue = ZonedDateTime.now().format(
                        DateTimeFormatter.ISO_INSTANT);
            }
            context.parameterBuilder().defaultValue(defaultValue);

            context.parameterBuilder().allowMultiple(
                    apiParam.get().allowMultiple());
            context.parameterBuilder().required(apiParam.get().required());
        }
    }

    private Function<ApiParam, String> toAllowableValue() {
        return new Function<ApiParam, String>() {
            @Override
            public String apply(ApiParam input) {
                return input.allowableValues();
            }
        };
    }

    private AllowableValues allowableValues(ResolvedType parameterType,
            String allowableValueString) {
        AllowableValues allowableValues = null;
        if (!isNullOrEmpty(allowableValueString)) {
            allowableValues = ApiModelProperties.allowableValueFromString(
                    allowableValueString);
        } else {
            if (parameterType.getErasedType().isEnum()) {
                allowableValues =
                        Enums.allowableValues(parameterType.getErasedType());
            }
            if (Collections.isContainerType(parameterType)) {
                allowableValues = Enums.allowableValues(
                        Collections.collectionElementType(
                                parameterType).getErasedType());
            }
        }
        return allowableValues;
    }
}
