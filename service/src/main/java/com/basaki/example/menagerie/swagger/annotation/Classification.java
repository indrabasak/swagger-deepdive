package com.basaki.example.menagerie.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by indra.basak on 5/1/17.
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
}
