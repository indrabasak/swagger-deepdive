package com.basaki.example.menagerie.config;

import com.basaki.example.menagerie.util.UuidBeanFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.util.UUID;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by indra.basak on 3/8/17.
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public static Mapper getMapper() {
        BeanMappingBuilder builder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(UUID.class, UUID.class, TypeMappingOptions.oneWay(),
                        TypeMappingOptions.beanFactory(
                                UuidBeanFactory.class.getName()));
            }
        };

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(builder);

        return mapper;
    }

    @Primary
    @Bean
    public ObjectMapper createCustomObjectMapper() {
        ObjectMapper mapper =  new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JodaModule());

        return mapper;
    }
}
