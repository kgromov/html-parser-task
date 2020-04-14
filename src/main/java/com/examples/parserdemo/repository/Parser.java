package com.examples.parserdemo.repository;

import com.examples.parserdemo.model.Item;

public interface Parser {

    Item parse(String input);
}
