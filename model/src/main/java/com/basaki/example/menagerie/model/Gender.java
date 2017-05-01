package com.basaki.example.menagerie.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by indra.basak on 4/29/17.
 */
public enum Gender {

    MALE, FEMALE;

    /**
     * Returns a <tt>Gender<tt> enum based on string matching
     *
     * @param value string stored in database
     * @return a matching <tt>Gender</tt>
     */
    @JsonCreator
    public static Gender fromValue(String value) {
        return valueOf(value.toUpperCase());
    }
}
