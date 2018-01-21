package com.company.project.tracker.model.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemTypeConverter implements AttributeConverter<ItemType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ItemType attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public ItemType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : ItemType.getById(dbData);
    }
}
