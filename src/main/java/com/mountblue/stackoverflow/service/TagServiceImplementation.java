package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Tag;
import com.mountblue.stackoverflow.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImplementation implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return this.tagRepository.findAll();
    }

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }
}

