package com.github.merelysnow.homes.commands;

import com.github.merelysnow.homes.HomesRepositories;
import com.github.merelysnow.homes.model.Homes;
import com.github.merelysnow.homes.model.User;
import com.github.merelysnow.homes.utils.Utils;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import me.lucko.helper.Schedulers;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class DelHomeCommand {

    @Command(
            name = "delhome",
            usage = "delhome <nome>",
            target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context, String name) {

        Player p = context.getSender();
        User user = HomesRepositories.CACHE_LOCAL.fetch(p.getName());

        if(!user.hasHome(name)) {
            p.sendMessage("§cVocê não possui uma home com esse nome.");
            return;
        }

        p.sendMessage(String.format("§eYeah! home '%s' deletada com sucesso.", name));
        user.getHomes().remove(name);
        Schedulers.async().runLater(() -> HomesRepositories.MYSQL.insert(user), 3, TimeUnit.SECONDS);
    }


}
