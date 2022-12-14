package com.github.merelysnow.homes.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.merelysnow.homes.model.Homes;
import com.github.merelysnow.homes.model.User;
import org.checkerframework.checker.nullness.qual.NonNull;

public class HomesLocalCache {

    private final @NonNull Cache<String, User> cache = Caffeine.newBuilder().build();

    public void validate(User user) {
        cache.put(user.getName(), user);
    }

    public User fetch(String name) {
        return cache.getIfPresent(name);
    }
}
