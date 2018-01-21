package com.company.project.tracker.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemRequest {

    @NotBlank(message = "Title cannot be blank")
    @Length(min = 5, max = 50, message = "Length must be between 5 and 50 characters long")
    private String title;

    @NotBlank(message = "Item description cannot be blank")
    private String description;

    @Range(min = 1, max = 3, message = "Item type id must be between 1 and 3")
    private Integer itemType;
}
