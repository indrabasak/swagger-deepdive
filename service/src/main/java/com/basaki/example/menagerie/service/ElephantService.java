package com.basaki.example.menagerie.service;

import com.basaki.example.menagerie.data.repository.MenagerieRepository;
import com.basaki.example.menagerie.model.animal.Elephant;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@code ElephantService} provides CRUD operations on an {@code Elephant}.
 * <p>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@Service
public class ElephantService extends AbstractMenagerieService<Elephant> {

    @Autowired
    public ElephantService(MenagerieRepository repo, Mapper mapper) {
        super(repo, mapper);
    }
}
