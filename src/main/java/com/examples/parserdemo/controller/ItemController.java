package com.examples.parserdemo.controller;

import com.examples.parserdemo.exceptions.NotFoundException;
import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import com.examples.parserdemo.repository.Parser;
import com.examples.parserdemo.service.PageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by konstantin on 11.04.2020.
 * Seems approach with controller per item (endpoint is much better)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final PageProvider provider;

    @GetMapping({"/parser/cars", "/cars"})
    public List<Item> getCars() {
        log.info("Getting all available cars");
        Parser parser = provider.getParser(ItemType.CAR);
        return Stream.of(1, 2, 3, 4)
                .map(id -> provider.getPageContent(ItemType.CAR, id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(parser::parse)
                .collect(Collectors.toList());
    }

    @GetMapping({"/parser/cars/{id}", "/cars/{id}"})
    public Item getItem(@PathVariable("id") Long id) {
        log.info("Getting car by id = {}", id);
        Parser parser = provider.getParser(ItemType.CAR); //could be easily replaced with direct creation via new
        String pageContent = provider.getPageContent(ItemType.CAR, id)
                .orElseThrow(() -> new NotFoundException(String.format("Apple product by id = %d is not found!", id)));
        return parser.parse(pageContent);
    }

    @GetMapping({"/parser/appleProducts", "/appleProducts"})
    public List<Item> getAppleProducts() {
        log.info("Getting all available apple products");
        Parser parser = provider.getParser(ItemType.APPLE);
        return Stream.of(1, 2, 3, 4)
                .map(id -> provider.getPageContent(ItemType.APPLE, id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(parser::parse)
                .collect(Collectors.toList());
    }

    @GetMapping({"/parser/appleProducts/{id}", "/appleProducts/{id}"})
    public Item getAppleProduct(@PathVariable("id") Long id) {
        log.info("Getting apple product by id = {}", id);
        Parser parser = provider.getParser(ItemType.APPLE);
        String pageContent = provider.getPageContent(ItemType.APPLE, id)
                .orElseThrow(() -> new NotFoundException(String.format("Apple product by id = %d is not found!", id)));
        return parser.parse(pageContent);
    }

    @GetMapping({"/parser/generalProducts", "/generalProducts"})
    public List<Item> getGeneralProducts() {
        log.info("Getting all available general products");
        Parser parser = provider.getParser(ItemType.GENERAL);
        return Stream.of(1, 2, 3, 4)
                .map(id -> provider.getPageContent(ItemType.GENERAL, id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(parser::parse)
                .collect(Collectors.toList());
    }

    @GetMapping({"/parser/generalProducts/{id}", "/generalProducts/{id}"})
    public Item getGeneralProduct(@PathVariable("id") Long id) {
        log.info("Getting general product by id = {}", id);
        Parser parser = provider.getParser(ItemType.GENERAL);
        String pageContent = provider.getPageContent(ItemType.GENERAL, id)
                .orElseThrow(() -> new NotFoundException(String.format("General product by id = %d is not found!", id)));
        return parser.parse(pageContent);
    }
}


