package com.basaki.example.menagerie.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code ApiOperationSince} is a custom swagger annotation to publish the since
 * object in swagger json.
 * <p>
 *
 * @author Indra Basak
 * @since 4/30/17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperationSince {
    String value() default "";

    String description() default "";
}
