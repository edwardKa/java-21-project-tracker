package com.company.project.tracker.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ITEM")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Convert(converter = ItemStatusConverter.class)
    private ItemStatus itemStatus;

    @Convert(converter = ItemTypeConverter.class)
    private ItemType itemType;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @CreationTimestamp
    private Date createdDate;

    @ManyToOne
    private User user;

    private String description;

    private String title;
}
