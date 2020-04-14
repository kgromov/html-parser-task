package com.examples.parserdemo.controller;

import com.examples.parserdemo.exceptions.NotFoundException;
import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import com.examples.parserdemo.repository.Parser;
import com.examples.parserdemo.service.PageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by konstantin on 11.04.2020.
 */
@Slf4j
@RestController
@RequestMapping({"/parser/cars", "/cars"})
public class CarsController {
    private final PageProvider provider;
    private final Parser parser;

    public CarsController(PageProvider provider, @Qualifier("carParser") Parser parser) {
        this.provider = provider;
        this.parser = parser;
    }

    @GetMapping()
    public List<Item> getItems() {
        log.info("Getting all available cars");
        return Stream.of(1, 2, 3, 4)
                .map(id -> provider.getPageContent(ItemType.CAR, id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(parser::parse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable("id") Long id) {
        log.info("Getting car by id = {}", id);
        String pageContent = provider.getPageContent(ItemType.CAR, id)
                .orElseThrow(() -> new NotFoundException(String.format("Car by id = %d is not found!", id)));
        return parser.parse(pageContent);
    }
}
