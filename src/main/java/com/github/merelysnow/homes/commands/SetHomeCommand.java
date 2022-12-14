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

public class SetHomeCommand {

    @Command(
            name = "sethome",
            usage = "sethome <nome>",
            target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context, String name) {

        Player p = context.getSender();
        MPlayer mp = MPlayer.get(p);
        User user = HomesRepositories.CACHE_LOCAL.fetch(p.getName());
        Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(p.getLocation()));

        if (user.hasHome(name)) {
            p.sendMessage("§cVocê já possui uma home com esse nome.");
            return;
        }

        if (factionAt != mp.getFaction() && !factionAt.isNone()) {
            p.sendMessage("§cVocê não pode setar uma em territorios de outra facção");
            return;
        }

        Homes home = new Homes(name, Utils.serializeLocation(p.getLocation()), false);

        user.getHomes().put(name, home);
        p.sendMessage(String.format("§eYeah! home '%s' criada com sucesso.", name));
        Schedulers.async().runLater(() -> HomesRepositories.MYSQL.insert(user), 3, TimeUnit.SECONDS);
    }


}
