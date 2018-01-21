package com.company.project.tracker.model.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemStatusConverter implements AttributeConverter<ItemStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ItemStatus attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public ItemStatus convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : ItemStatus.getById(dbData);
    }
}
