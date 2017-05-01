package com.basaki.example.menagerie.service;

import com.basaki.example.menagerie.data.repository.MenagerieRepository;
import com.basaki.example.menagerie.model.bird.Toucan;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by indra.basak on 4/30/17.
 */
@Service
public class ToucanService extends AbstractMenagerieService<Toucan> {

    @Autowired
    public ToucanService(MenagerieRepository repo, Mapper mapper) {
        super(repo, mapper);
    }
}
