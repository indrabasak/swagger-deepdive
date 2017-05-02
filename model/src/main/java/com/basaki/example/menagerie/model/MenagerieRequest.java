package com.basaki.example.menagerie.model;

import lombok.Data;

/**
 * {@code MenagerieRequest} represents a request to create or update a menagerie
 * model.
 * <p>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@Data
public class MenagerieRequest {
    private String name;
    private Gender gender;
}
