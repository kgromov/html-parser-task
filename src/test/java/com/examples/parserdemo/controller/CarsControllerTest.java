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

public class CarsControllerTest {
    @Mock
    private Parser parser;
    @Mock
    private PageProvider provider;
    private CarsController controller;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new CarsController(provider, parser);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getItem() throws Exception {
        Item car = new Item("Bugatti-Veyron", "Bugatti", "$1000000");
        when(parser.parse(anyString())).thenReturn(car);
        when(provider.getPageContent(eq(ItemType.CAR), anyLong())).thenReturn(Optional.of("dummy"));
        MvcResult result = mockMvc.perform(get("/parser/cars/1"))
                .andExpect(status().isOk())
                .andReturn();
        Item item = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Item.class);
        assertEquals(car, item);
    }

    @Test
    public void getItems() throws Exception {
        Item car = new Item("Bugatti-Veyron", "Bugatti", "$1000000");
        when(parser.parse(anyString())).thenReturn(car);
        when(provider.getPageContent(eq(ItemType.CAR), anyLong())).thenReturn(Optional.of("dummy"));
        MvcResult result = mockMvc.perform(get("/parser/cars"))
                .andExpect(status().isOk())
                .andReturn();
        List<Item> items = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Item>>() {});
        assertNotNull(items);
        verify(parser, times(4)).parse(anyString());
        verify(provider, times(4)).getPageContent(eq(ItemType.CAR), anyLong());
    }

}