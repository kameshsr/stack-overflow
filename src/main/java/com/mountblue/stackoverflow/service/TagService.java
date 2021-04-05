package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    void save(Tag tag);
}
