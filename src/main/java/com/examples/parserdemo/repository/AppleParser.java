package com.examples.parserdemo.repository;

import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Repository;

/**
 * Created by konstantin on 10.04.2020.
 */
@Slf4j
@Repository("appleParser")
public class AppleParser implements Parser {

    @Override
    public Item parse(String input) {
        Document document = Jsoup.parse(input);
        String name = document.getElementById("productTitle").text();;
        String category = document.getElementById("bylineInfo").text();
        String price = document.getElementById("priceblock_ourprice").text();
        return new Item(name, category, price);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.APPLE;
    }
}
