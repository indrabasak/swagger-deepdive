package com.basaki.example.menagerie.service;

import com.basaki.example.menagerie.data.repository.MenagerieRepository;
import com.basaki.example.menagerie.model.animal.Lion;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@code LionService} provides CRUD operations on a {@code Lion}.
 * <p>
 *
 * @author Indra Basak
 * @since 4/30/17
 */
@Service
public class LionService extends AbstractMenagerieService<Lion> {

    @Autowired
    public LionService(MenagerieRepository repo, Mapper mapper) {
        super(repo, mapper);
    }
}