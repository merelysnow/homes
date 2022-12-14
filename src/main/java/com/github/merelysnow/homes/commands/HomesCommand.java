package com.github.merelysnow.homes.commands;

import com.github.merelysnow.homes.HomesRepositories;
import com.github.merelysnow.homes.model.Homes;
import com.github.merelysnow.homes.model.User;
import com.massivecraft.factions.entity.MPlayer;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

public class HomesCommand {

    @Command(
            name = "homes",
            target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context, @Optional String name) {

        Player p = context.getSender();
        MPlayer mp = MPlayer.get(p);
        User user = HomesRepositories.CACHE_LOCAL.fetch(p.getName());

        if(name != null) {

            User targetUser = HomesRepositories.CACHE_LOCAL.fetch(name);

            if(!(mp.isOverriding())) {
                p.sendMessage("§cPara ver as homes do jogador alvo é necessario estar no modo admin.");
                return;
            }

            p.sendMessage("§eHomes do jogador " + name);
            p.sendMessage(" ");
            p.sendMessage("§eHomes: " + targetUser.getHomes().keySet());
            return;
        }

        p.sendMessage("§eSuas homes");
        p.sendMessage(" ");
        p.sendMessage("§eHomes: " + user.getHomes().keySet());
    }
}
