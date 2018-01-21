package com.company.project.tracker.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ItemType {
    TASK(1),
    BUG(2),
    TEST(3);

    private Integer id;


    public static ItemType getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Item type id cannot be null");
        }

        for (ItemType itemType : values()) {
            if (itemType.getId().equals(id)) {
                return itemType;
            }
        }

        return null;
    }

}
