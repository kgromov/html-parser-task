package com.examples.parserdemo.repository;

import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import com.examples.parserdemo.service.PageProvider;
import org.springframework.beans.factory.annotation.Autowired;

public interface Parser {

    Item parse(String input);

    ItemType getItemType();

    @Autowired
    default void autoRegister(PageProvider provider)
    {
        provider.registerParser(getItemType(), this);
    }
}
