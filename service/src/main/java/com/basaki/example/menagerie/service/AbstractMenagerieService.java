package com.basaki.example.menagerie.service;

import com.basaki.example.menagerie.data.entity.MenagerieEntity;
import com.basaki.example.menagerie.data.entity.Species;
import com.basaki.example.menagerie.data.repository.MenagerieRepository;
import com.basaki.example.menagerie.error.DataNotFoundException;
import com.basaki.example.menagerie.model.Gender;
import com.basaki.example.menagerie.model.MenagerieModel;
import com.basaki.example.menagerie.model.MenagerieRequest;
import com.basaki.example.menagerie.model.animal.Elephant;
import com.basaki.example.menagerie.model.animal.Lion;
import com.basaki.example.menagerie.model.animal.Tiger;
import com.basaki.example.menagerie.model.bird.Parrot;
import com.basaki.example.menagerie.model.bird.Toucan;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

/**
 * {@code AbstractMenagerieService} represents an menagerie service which
 * provides all the CRUD opeartions by interacting with the repository.
 * <p>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@Slf4j
public abstract class AbstractMenagerieService<T extends MenagerieModel> {

    private final MenagerieRepository repo;

    private final Mapper mapper;

    private final Class<T> clazz;

    private Species species;

    public AbstractMenagerieService(MenagerieRepository repo, Mapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
        clazz = getTypeParameterClass();
        if (clazz.isAssignableFrom(Elephant.class)) {
            species = Species.ELEPHANT;
        } else if (clazz.isAssignableFrom(Lion.class)) {
            species = Species.LION;
        } else if (clazz.isAssignableFrom(Tiger.class)) {
            species = Species.TIGER;
        } else if (clazz.isAssignableFrom(Parrot.class)) {
            species = Species.PARROT;
        } else if (clazz.isAssignableFrom(Toucan.class)) {
            species = Species.TOUCAN;
        }
    }

    @Transactional
    public T create(MenagerieRequest request) {
        validate(request);
        MenagerieEntity entity = mapper.map(request, MenagerieEntity.class);
        entity.setId(UUID.randomUUID());
        entity.setSpecies(species);

        entity = repo.save(entity);
        T model = mapEntityToModel(entity);

        return model;
    }

    public T read(UUID id) {
        MenagerieEntity entity = repo.findDistinctByIdAndSpecies(id, species);

        if (entity == null) {
            throw new DataNotFoundException(
                    species + "with id " + id + " not found!");
        }

        T model = mapEntityToModel(entity);

        return model;
    }

    public List<T> readAll(String name, Gender gender) {
        if (name == null && gender == null) {
            return mapEntitiesToModels(repo.findBySpecies(species));
        }

        MenagerieEntity entity = new MenagerieEntity();
        entity.setSpecies(species);
        ExampleMatcher matcher =
                ExampleMatcher.matching().withMatcher("title", contains());

        if (name != null) {
            entity.setName(name);
            matcher = matcher.withMatcher("name", startsWith().ignoreCase());
        }

        if (gender != null) {
            entity.setGender(gender);
        }

        Example<MenagerieEntity> example = Example.of(entity, matcher);

        return mapEntitiesToModels(repo.findAll(example));
    }

    @Transactional
    public T update(UUID id, MenagerieRequest request) {
        MenagerieEntity entity = repo.findDistinctByIdAndSpecies(id, species);

        if (entity == null) {
            throw new DataNotFoundException(
                    species + "with id " + id + " not found!");
        }

        validate(request);
        mapper.map(request, entity);
        entity = repo.save(entity);

        T model = mapEntityToModel(entity);

        log.info("Updated " + species + " book with id " + model.getId());

        return model;
    }

    @Transactional
    public void delete(UUID id) {
        try {
            repo.deleteByIdAndSpecies(id, species);
        } catch (Exception e) {
            throw new DataNotFoundException(
                    species + " with id " + id + " not found!");
        }
    }

    @Transactional
    public void deleteAll() {
        repo.deleteBySpecies(species);
    }

    private void validate(MenagerieRequest request) {
        Assert.notNull(request.getName(), "Name cannot be null.");
        Assert.notNull(request.getGender(), "Gender cannot be null.");
    }

    private T mapEntityToModel(MenagerieEntity entity) {
        T model = mapper.map(entity, clazz);
        return model;
    }

    private List<T> mapEntitiesToModels(List<MenagerieEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            throw new DataNotFoundException(
                    "No " + species + " found with the search criteria!");
        }

        return entities.stream().map(e -> mapEntityToModel(e)).collect(
                Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Class<T> getTypeParameterClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }
}
