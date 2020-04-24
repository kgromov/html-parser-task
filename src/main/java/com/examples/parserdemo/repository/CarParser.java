package com.examples.parserdemo.repository;

import com.examples.parserdemo.model.Item;
import com.examples.parserdemo.model.ItemType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Repository;

/**
 * Created by konstantin on 10.04.2020.
 */
@Slf4j
@Repository("carParser")
public class CarParser implements Parser {

    @Override
    public Item parse(String input) {
        Document document = Jsoup.parse(input);
        Element productTitle = document.getElementById("product-title");
        String name = productTitle.getElementsByTag("h1").get(0).text();
        String category = productTitle.parent().getElementsByTag("a").stream()
                .map(Element::text)
                .findFirst()
                .orElse(null);
        String price = document.select("#product-price span.a-size-base-plus").get(0).text();
        return new Item(name, category, price);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.CAR;
    }
}
