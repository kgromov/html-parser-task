package com.examples.parserdemo.service;

import com.examples.parserdemo.model.ItemType;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by konstantin on 10.04.2020.
 */
@Slf4j
@Repository
@NoArgsConstructor
public class PageProvider {
    private static final String HTML_PAGE_PLACEHOLDER = "static/amazon/%s/%02d.html";

    @SneakyThrows
    @Cacheable("requests")
    public Optional<String> getPageContent(ItemType type, long id) {
        log.info("Finding item by type = {} and id = {}", type.getValue(), id);
        String resourcePath = String.format(HTML_PAGE_PLACEHOLDER, type.getValue(), id);
        URL resource = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if (resource == null)
        {
            log.warn("No page found by type {} and id = {}", type.getValue(), id);
            return Optional.empty();
        }
        Path pathToPage = Paths.get(resource.toURI());
        return Optional.of(new String(Files.readAllBytes(pathToPage)));
    }
}
