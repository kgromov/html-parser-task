package com.examples.parserdemo.controller;

import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import com.examples.parserdemo.repository.AppleParser;
import com.examples.parserdemo.service.PageProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppleGoodsControllerTest {
    @Mock
    private AppleParser parser;
    @Mock
    private PageProvider provider;
    private AppleGoodsController controller;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new AppleGoodsController(provider, parser);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getItem() throws Exception {
        Item iPhone = new Item("IphoneX", "Apple", "$1000");

        when(parser.parse(anyString())).thenReturn(iPhone);
        when(provider.getPageContent(eq(ItemType.APPLE), anyLong())).thenReturn(Optional.of("dummy"));
        MvcResult result = mockMvc.perform(get("/parser/appleProducts/4"))
                .andExpect(status().isOk())
                .andReturn();
        Item item = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Item.class);
        assertEquals(iPhone, item);
    }

    @Test
    public void getItems() throws Exception {
        Item iPhone = new Item("IphoneX", "Apple", "$1000");
        when(parser.parse(anyString())).thenReturn(iPhone);
        when(provider.getPageContent(eq(ItemType.APPLE), anyLong())).thenReturn(Optional.of("dummy"));
        MvcResult result = mockMvc.perform(get("/parser/appleProducts"))
                .andExpect(status().isOk())
                .andReturn();
        List<Item> items = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Item>>() {});
        assertNotNull(items);
        verify(parser, times(4)).parse(anyString());
        verify(provider, times(4)).getPageContent(eq(ItemType.APPLE), anyLong());
    }


}