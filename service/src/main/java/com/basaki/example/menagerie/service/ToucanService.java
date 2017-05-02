package com.basaki.example.menagerie.service;

import com.basaki.example.menagerie.data.repository.MenagerieRepository;
import com.basaki.example.menagerie.model.bird.Toucan;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@codeToucanService} provides CRUD operations on a {@code Toucan}.
 * <p>
 *
 * @author Indra Basak
 * @since 4/30/17
 */
@Service
public class ToucanService extends AbstractMenagerieService<Toucan> {

    @Autowired
    public ToucanService(MenagerieRepository repo, Mapper mapper) {
        super(repo, mapper);
    }
}
