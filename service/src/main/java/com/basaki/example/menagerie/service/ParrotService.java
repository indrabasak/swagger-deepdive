package com.basaki.example.menagerie.service;

import com.basaki.example.menagerie.data.repository.MenagerieRepository;
import com.basaki.example.menagerie.model.bird.Parrot;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@code ParrorService} provides CRUD operations on a {@code Parrot}.
 * <p>
 *
 * @author Indra Basak
 * @since 4/30/17
 */
@Service
public class ParrotService extends AbstractMenagerieService<Parrot> {

    @Autowired
    public ParrotService(MenagerieRepository repo, Mapper mapper) {
        super(repo, mapper);
    }
}
