package com.examples.parserdemo.service;

import com.examples.parserdemo.model.ItemType;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class PageProviderTest {

    @Test
    public void getPageContent() {
        PageProvider provider = new PageProvider();
        Optional<String> pageContent = provider.getPageContent(ItemType.APPLE, 1L);
        assertTrue(pageContent.isPresent());
        assertFalse(pageContent.get().isEmpty());
    }

    @Test
    public void getPageEmptyContent() {
        PageProvider provider = new PageProvider();
        Optional<String> pageContent = provider.getPageContent(ItemType.APPLE, 21L);
        assertFalse(pageContent.isPresent());
    }
}