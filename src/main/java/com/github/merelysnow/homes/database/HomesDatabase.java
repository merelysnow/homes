package com.github.merelysnow.homes.database;

import com.github.merelysnow.homes.HomesPlugin;
import com.github.merelysnow.homes.model.Homes;
import com.github.merelysnow.homes.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class HomesDatabase {

    private static final String TABLE  = "homes_table";
    private static final Gson GSON = new GsonBuilder().create();

    public void handleTable() {
        executor().updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "name VARCHAR(16) NOT NULL," +
                "homes TEXT NOT NULL" +
                ");");
    }

    public void insert(User user) {
        executor().updateQuery("REPLACE INTO " + TABLE + " VALUES(?,?)",
                simpleStatement -> {
                    simpleStatement.set(1, user.getName());
                    simpleStatement.set(2, GSON.toJson(user.getHomes()));
                });
    }

    public Set<User> fetchAll() {
        return executor().resultManyQuery(
                "SELECT * FROM " + TABLE,
                simpleStatement -> {},
                MobCoinsDatabaseAdapter.class
        );
    }

    private SQLExecutor executor()  {
        return new SQLExecutor(HomesPlugin.plugin.getCONNECTOR());
    }

    public static class MobCoinsDatabaseAdapter implements SQLResultAdapter<User> {

        @Override
        public User adaptResult(SimpleResultSet resultSet) {

            Type type = new TypeToken<HashMap<String, Homes>>() {}.getType();

            return new User(
                    resultSet.get("name"),
                    GSON.fromJson((String) resultSet.get("homes"), type)
            );
        }
    }
}
