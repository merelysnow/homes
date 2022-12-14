package com.github.merelysnow.homes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class User {

    private final String name;
    private HashMap<String, Homes> homes;

    public boolean hasHome(String name) {

        for (Homes home : homes.values()) {
            if (name.equals(home.getName())) {
                return true;
            }
        }

        return false;
    }
}
