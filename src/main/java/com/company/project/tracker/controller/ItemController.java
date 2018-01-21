package com.company.project.tracker.controller;

import com.company.project.tracker.exception.InputValidationException;
import com.company.project.tracker.model.entity.Item;
import com.company.project.tracker.model.entity.User;
import com.company.project.tracker.model.web.ItemRequest;
import com.company.project.tracker.service.AuthenticationService;
import com.company.project.tracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Item> getItems(@RequestParam(value = "my-items", required = false) Boolean isMyItems,
                               @RequestHeader("Authorization") String header) {
        User authenticatedUser = authenticationService.getAuthenticatedUser(header);
        if (Boolean.TRUE.equals(isMyItems)) {
            return itemService.getByUser(authenticatedUser);
        }
        return itemService.getAll();
    }

    @GetMapping(value = "/{itemId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Item getById(@PathVariable("itemId") Integer itemId) {
        return itemService.getItemById(itemId);
    }


    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Integer itemId) {
        itemService.delete(itemId);
    }

    @PostMapping("/")
    public Item create(@RequestBody @Valid ItemRequest itemRequest,
                       BindingResult result,
                       @RequestHeader("Authorization") String header) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        User user = getAuthenticatedUser(header);
        return itemService.create(
                user,
                itemRequest.getTitle(),
                itemRequest.getDescription(),
                itemRequest.getItemType());
    }

    @PutMapping("/done/{itemId}")
    public Item itemDone(@PathVariable("itemId") Integer itemId) {
        return itemService.done(itemId);
    }


    private User getAuthenticatedUser(String header) {
        return authenticationService.getAuthenticatedUser(header);
    }




}
