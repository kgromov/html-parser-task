package com.examples.parserdemo.repository;

import com.examples.parserdemo.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by konstantin on 10.04.2020.
 */
@Slf4j
@Repository("generalParser")
public class GeneralParser implements Parser {

    @Override
    public Item parse(String input) {
        Document document = Jsoup.parse(input);
        String name = document.getElementById("productTitle").text();
        String divider = String.format(" %s ", document.getElementsByClass("a-breadcrumb-divider").stream()
                .map(Element::text)
                .findFirst()
                .orElse(">"));
        String category = document.select(".a-list-item .a-color-tertiary").stream()
                .map(Element::text)
                .collect(Collectors.joining(divider));

        String price = Optional.ofNullable(document.getElementById("newBuyBoxPrice"))
                .orElse(document.selectFirst(".offer-price")).text();
        return new Item(name, category, price);
    }
}
