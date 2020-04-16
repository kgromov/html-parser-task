package com.examples.parserdemo.controller;

import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import com.examples.parserdemo.repository.AppleParser;
import com.examples.parserdemo.repository.Parser;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GeneralGoodsControllerTest {
    @Mock
    private Parser parser;
    @Mock
    private PageProvider provider;
    private GeneralGoodsController controller;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new GeneralGoodsController(provider, parser);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getItem() throws Exception {
        Item expectedItem = new Item("Super Camera XXX", "Camera", "$1000");
        when(parser.parse(anyString())).thenReturn(expectedItem);
        when(provider.getPageContent(eq(ItemType.GENERAL), anyLong())).thenReturn(Optional.of("dummy"));
        MvcResult result = mockMvc.perform(get("/parser/generalProducts/4"))
                .andExpect(status().isOk())
                .andReturn();
        Item item = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Item.class);
        assertEquals(expectedItem, item);
    }

    @Test
    public void getItems() throws Exception {
        Item expectedItem = new Item("Super Camera XXX", "Camera", "$1000");
        when(parser.parse(anyString())).thenReturn(expectedItem);
        when(provider.getPageContent(eq(ItemType.GENERAL), anyLong())).thenReturn(Optional.of("dummy"));
        MvcResult result = mockMvc.perform(get("/parser/generalProducts"))
                .andExpect(status().isOk())
                .andReturn();
        List<Item> items = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Item>>() {});
        assertNotNull(items);
        verify(parser, times(4)).parse(anyString());
        verify(provider, times(4)).getPageContent(eq(ItemType.GENERAL), anyLong());
    }


}