package com.basaki.example.menagerie.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code Classification} is a custom swagger annotation to publish the
 * classification of a menagerie resident type as a swagger json object.
 * <p>
 *
 * @author Indra Basak
 * @since 5/1/17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Classification {
    String kingdom() default "";

    String phylum() default "";

    String clazz() default "";

    String order() default "";

    String family() default "";

    String genus() default "";

    String species() default "";
}
