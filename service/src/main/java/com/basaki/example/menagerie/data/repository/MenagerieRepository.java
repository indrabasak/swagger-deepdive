package com.basaki.example.menagerie.data.repository;

/**
 * Created by indra.basak on 4/29/17.
 */

import com.basaki.example.menagerie.data.entity.MenagerieEntity;
import com.basaki.example.menagerie.data.entity.Species;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@code MenagerieRepository} exposes all CRUD operations on a data of type
 * {@code MenagerieEntity}.
 * <p/>
 *
 * @author Indra Basak
 * @since on 4/29/17
 */
@Repository
public interface MenagerieRepository extends JpaRepository<MenagerieEntity, UUID> {

    MenagerieEntity findDistinctByIdAndSpecies(UUID id, Species species);

    List<MenagerieEntity> findBySpecies(Species species);

    void deleteByIdAndSpecies(UUID id, Species species);

    void deleteBySpecies(Species species);
}
