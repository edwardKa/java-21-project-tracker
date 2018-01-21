package com.company.project.tracker.repository;

import com.company.project.tracker.model.entity.Item;
import com.company.project.tracker.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByUser(User user);
}
