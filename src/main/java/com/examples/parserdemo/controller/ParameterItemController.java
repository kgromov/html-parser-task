package com.examples.parserdemo.controller;

import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import com.examples.parserdemo.repository.Parser;
import com.examples.parserdemo.service.PageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/**/items"})
public class ParameterItemController {
    private final PageProvider provider;

    @GetMapping
    public List<Item>  getItem(@RequestParam(name = "type") String type,
                               @RequestParam(name = "page", required = false) Integer pageIndex)
    {
        ItemType itemType = ItemType.valueOf(type.toUpperCase());
        log.info("Getting all available {} products by ids = {}", itemType, pageIndex);
        Parser parser = provider.getParser(itemType);
        return Stream.of(1, 2, 3, 4)
                .filter(id -> Objects.isNull(pageIndex) || id.equals(pageIndex))
                .map(id -> provider.getPageContent(itemType, id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(parser::parse)
                .collect(Collectors.toList());
    }
}
