package com.company.project.tracker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ItemStatus {
    TODO(1),
    DONE(2)
    ;

    private Integer id;

    public static ItemStatus getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Item status cannot be null");
        }

        for (ItemStatus itemStatus : values()) {
            if (itemStatus.getId().equals(id)) {
                return itemStatus;
            }
        }

        return null;
    }

}
