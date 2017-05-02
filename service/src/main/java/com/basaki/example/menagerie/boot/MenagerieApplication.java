package com.basaki.example.menagerie.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@code MenagerieApplication} represents the entry point for menagerie spring
 * boot application.
 * <p>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.basaki.example.menagerie.config",
        "com.basaki.example.menagerie.controller",
        "com.basaki.example.menagerie.data.entity",
        "com.basaki.example.menagerie.data.repository",
        "com.basaki.example.menagerie.error",
        "com.basaki.example.menagerie.model",
        "com.basaki.example.menagerie.service",
        "com.basaki.example.menagerie.swagger.annotation",
        "com.basaki.example.menagerie.swagger.plugin"})
public class MenagerieApplication {
    public static void main(String[] args) {
        SpringApplication.run(MenagerieApplication.class, args);
    }
}
