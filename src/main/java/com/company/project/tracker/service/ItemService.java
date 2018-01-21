package com.company.project.tracker.service;

import com.company.project.tracker.model.entity.Item;
import com.company.project.tracker.model.entity.User;

import java.util.List;

public interface ItemService {

    Item create(User user, String title, String description, Integer itemType);

    Item done(Integer itemId);

    List<Item> getAll();

    List<Item> getByUser(User user);

    Item getItemById(Integer id);

    void delete(Integer itemId);
}
