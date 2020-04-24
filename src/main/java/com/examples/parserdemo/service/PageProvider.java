package com.examples.parserdemo.service;

import com.examples.parserdemo.model.ItemType;
import com.examples.parserdemo.repository.Parser;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by konstantin on 10.04.2020.
 */
@Slf4j
@Repository
@NoArgsConstructor
public class PageProvider {
    private static final String HTML_PAGE_PLACEHOLDER = "static/amazon/%s/%02d.html";
    private final Map<ItemType, Parser> parsers = new EnumMap<>(ItemType.class);

    public void registerParser(ItemType type, Parser parser) {
        parsers.put(type, parser);
    }

    public Parser getParser(ItemType type) {
        return Optional.ofNullable(parsers.get(type))
                .orElseThrow(() -> new UnsupportedOperationException("Parser for type = " + type + " not found"));
    }

    @SneakyThrows
    @Cacheable("requests")
    public Optional<String> getPageContent(ItemType type, long id) {
        log.info("Finding item by type = {} and id = {}", type.getValue(), id);
        String resourcePath = String.format(HTML_PAGE_PLACEHOLDER, type.getValue(), id);
        URL resource = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if (resource == null) {
            log.warn("No page found by type {} and id = {}", type.getValue(), id);
            return Optional.empty();
        }
        Path pathToPage = Paths.get(resource.toURI());
        return Optional.of(new String(Files.readAllBytes(pathToPage)));
    }
}
