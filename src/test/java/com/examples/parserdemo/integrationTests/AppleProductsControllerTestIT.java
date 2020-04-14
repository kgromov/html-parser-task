package com.examples.parserdemo.integrationTests;

import com.examples.parserdemo.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppleProductsControllerTestIT {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getItems() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/parser/appleProducts"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        List<Item> items = new ObjectMapper().readValue(contentAsString, new TypeReference<List<Item>>() {});
        Assert.assertNotNull(items);
        Assert.assertEquals(4, items.size());
        Item item = items.get(2);
        Assert.assertEquals("Apple MagSafe to MagSafe 2 Converter", item.getName());
        Assert.assertEquals("Apple", item.getCategory());
        Assert.assertEquals("$9.99", item.getPrice());
    }

    @Test
    public void getItem() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/parser/appleProducts/4"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Item item = new ObjectMapper().readValue(contentAsString, Item.class);
        Assert.assertNotNull(item);
        Assert.assertEquals("Apple Magic Keyboard with Numeric Keypad (Wireless, Rechargable) (US English) - Silver", item.getName());
        Assert.assertEquals("Apple", item.getCategory());
        Assert.assertEquals("$119.00", item.getPrice());
    }

    @Test
    public void getItemBadRequest() throws Exception {
        mockMvc.perform(get("/parser/appleProducts/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void getItemNotFound() throws Exception {
        mockMvc.perform(get("/parser/appleProducts/121"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }
}