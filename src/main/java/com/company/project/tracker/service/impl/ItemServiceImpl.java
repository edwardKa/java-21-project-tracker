package com.company.project.tracker.service.impl;

import com.company.project.tracker.exception.ItemDoesNotExistException;
import com.company.project.tracker.model.entity.Item;
import com.company.project.tracker.model.entity.ItemStatus;
import com.company.project.tracker.model.entity.ItemType;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.repository.ItemRepository;
import com.company.project.tracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item create(User user, String title, String description, Integer itemType) {
        Item item = Item.builder()
                .description(description)
                .title(title)
                .itemType(ItemType.getById(itemType))
                .itemStatus(ItemStatus.TODO)
                .user(user)
                .createdDate(new Date())
                .build();

        return itemRepository.save(item);
    }

    @Override
    public Item done(Integer itemId) {
        Item item = itemRepository.findOne(itemId);
        if (item == null) {
            throw new ItemDoesNotExistException("Item with ID " + itemId + " does not exist");
        }
        item.setItemStatus(ItemStatus.DONE);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getByUser(User user) {
        return itemRepository.findAllByUser(user);
    }

    @Override
    public Item getItemById(Integer id) {
        return itemRepository.findOne(id);
    }

    @Override
    public void delete(Integer itemId) {
        Item item = itemRepository.findOne(itemId);
        if (item == null) {
            throw new ItemDoesNotExistException("Item with Id" + itemId + " does not exist");
        }
        itemRepository.delete(itemId);
    }
}
