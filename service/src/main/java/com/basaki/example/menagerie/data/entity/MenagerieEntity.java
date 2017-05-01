package com.basaki.example.menagerie.data.entity;

import com.basaki.example.menagerie.model.Gender;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by indra.basak on 4/29/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menagerie")
public class MenagerieEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "species", nullable = false)
    private Species species;
}
