package com.github.merelysnow.homes.commands;

import com.github.merelysnow.homes.HomesRepositories;
import com.github.merelysnow.homes.model.Homes;
import com.github.merelysnow.homes.model.User;
import me.lucko.helper.Schedulers;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PublicCommand {

    @Command(
            name = "publica",
            usage = "publica <name>",
            target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context, String name) {

        Player p = context.getSender();
        User user = HomesRepositories.CACHE_LOCAL.fetch(p.getName());

        if (!user.hasHome(name)) {
            p.sendMessage("§cVocê não possui essa home.");
            return;
        }

        Homes home = user.getHomes().get(name);

        home.setGeneral(true);
        p.sendMessage(String.format("§eYeah! A home '%s' agora é publica.", home.getName()));
        Schedulers.async().runLater(() -> HomesRepositories.MYSQL.insert(user), 3, TimeUnit.SECONDS);
    }
}
