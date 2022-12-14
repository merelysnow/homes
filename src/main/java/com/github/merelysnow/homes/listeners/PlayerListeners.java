package com.github.merelysnow.homes.listeners;

import com.github.merelysnow.homes.HomesRepositories;
import com.github.merelysnow.homes.model.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerListeners implements Listener {

    @EventHandler
    void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        User user = HomesRepositories.CACHE_LOCAL.fetch(p.getName());

        if (user == null) {
            user = new User(p.getName(), new HashMap<>());

            HomesRepositories.CACHE_LOCAL.validate(user);
            HomesRepositories.MYSQL.insert(user);
        }
    }
}
