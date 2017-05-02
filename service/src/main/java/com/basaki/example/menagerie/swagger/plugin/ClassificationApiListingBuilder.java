package com.basaki.example.menagerie.swagger.plugin;

import com.basaki.example.menagerie.swagger.annotation.Classification;
import com.fasterxml.classmate.types.ResolvedObjectType;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.schema.Model;
import springfox.documentation.schema.ModelProperty;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static springfox.documentation.service.Tags.tagNameComparator;

/**
 * Created by indra.basak on 5/1/17.
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1001)
public class ClassificationApiListingBuilder implements ApiListingBuilderPlugin {

    @Override
    public void apply(ApiListingContext context) {
        Classification
                annotation = findAnnotation(
                context.getResourceGroup().getControllerClass(),
                Classification.class);
        if (annotation != null) {
            addTagName(context, annotation);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    private void addModel(ApiListingContext context, Classification clsfn) {
        ApiListingBuilder builder = context.apiListingBuilder();

        Field field =
                ReflectionUtils.findField(builder.getClass(), "models");
        ReflectionUtils.makeAccessible(field);
        Object value = Optional.ofNullable(
                ReflectionUtils.getField(field, builder)).orElse(
                Collections.emptyMap());
        Map<String, Model> models = (Map<String, Model>) value;
        Map<String, Model> newModels = new HashMap<>(models);
        newModels.put("Classification", createModel(clsfn));
        builder.models(newModels);
    }

    private Model createModel(Classification clsfn) {
        String id = "Classification";
        String name = "Classification";
        ResolvedObjectType
                type =
                ResolvedObjectType.create(Classification.class, null, null,
                        null);
        String qualifiedType = clsfn.getClass().getCanonicalName();
        String description = "";
        String baseModel = "";
        String discriminator = "";
        List<String> subTypes = Collections.EMPTY_LIST;
        String example = null;
        Map<String, ModelProperty> properties = new HashMap<>();

        if (clsfn.kingdom() != null) {
            ModelProperty prop = new ModelProperty("kingdom",
                    ResolvedObjectType.create(String.class, null, null, null),
                    "java.lang.String", 0, false, false, true, null, null, null,
                    null);
            properties.put("kingdom", prop);
        }

        Model model = new Model(id, name, type, qualifiedType, properties,
                description, baseModel, discriminator, subTypes, example);

        return model;
    }

    private void addTag(ApiListingContext context, Classification clsfn) {
        ApiListingBuilder builder = context.apiListingBuilder();

        Field field =
                ReflectionUtils.findField(builder.getClass(), "tags");
        ReflectionUtils.makeAccessible(field);
        Object value = Optional.ofNullable(
                ReflectionUtils.getField(field, builder)).orElse(
                Collections.EMPTY_SET);
        Set<Tag> tags = (Set<Tag>) value;
        Set<Tag> newTags = newTreeSet(tagNameComparator());
        newTags.addAll(tags);
        newTags.add(createTag(clsfn));
        builder.tags(newTags);
    }

    private Tag createTag(Classification clsfn) {
        Tag tag = new Tag("classification", clsfn.toString());
        return tag;
    }

    private void addTagName(ApiListingContext context, Classification clsfn) {
        ApiListingBuilder builder = context.apiListingBuilder();
        Set<String> newTagNames = newHashSet();
        String value = clsfn.toString();
        int index = value.indexOf("Classification");
        if (index >= 0) {
            value = value.substring(index);
        }
        newTagNames.add(value);
        builder.tagNames(newTagNames);
    }

}
