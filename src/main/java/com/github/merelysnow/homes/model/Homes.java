package com.github.merelysnow.homes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Homes {

    private final String name;
    private String location;
    private boolean general;

}
