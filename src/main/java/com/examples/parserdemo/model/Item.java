package com.examples.parserdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Item implements Serializable {
    private String name;
    private String category;
    private String price;
}
