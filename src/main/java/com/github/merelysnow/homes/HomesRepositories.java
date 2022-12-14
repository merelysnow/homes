package com.github.merelysnow.homes;


import com.github.merelysnow.homes.database.HomesDatabase;
import com.github.merelysnow.homes.local.HomesLocalCache;
import com.github.merelysnow.homes.model.User;

public class HomesRepositories {

    public static HomesLocalCache CACHE_LOCAL;
    public static HomesDatabase MYSQL;

    public HomesRepositories() {
        CACHE_LOCAL = new HomesLocalCache();
        MYSQL = new HomesDatabase();

        MYSQL.handleTable();
        for (User allUser : MYSQL.fetchAll()) {
            CACHE_LOCAL.validate(allUser);
        }
    }

}
