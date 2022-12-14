package com.github.merelysnow.homes.commands;

import com.github.merelysnow.homes.HomesRepositories;
import com.github.merelysnow.homes.model.Homes;
import com.github.merelysnow.homes.model.User;
import com.github.merelysnow.homes.utils.Utils;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HomeCommand {

    @Command(
            name = "home",
            usage = "home <nome>",
            target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context, String name) {

        Player p = context.getSender();
        MPlayer mp = MPlayer.get(p);
        User user = HomesRepositories.CACHE_LOCAL.fetch(p.getName());

        if (name.contains(":")) {

            String[] split = name.split(":");
            User targetUser = HomesRepositories.CACHE_LOCAL.fetch(split[0]);

            if (targetUser == null) {
                p.sendMessage("§cEsse jogador não foi encontrado em nosso banco de dados.");
                return;
            }

            if (split.length == 1) {
                p.sendMessage(String.format("§eHomes publicas do jogador %s", split[0]));
                p.sendMessage(" ");
                Utils.listPublicHomes(p, targetUser);
                return;
            }

            if (!targetUser.hasHome(split[1])) {
                p.sendMessage("§cVocê não possui essa home.");
                return;
            }

            Homes home = targetUser.getHomes().get(split[1]);
            Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(Utils.deserializeLocation(home.getLocation())));

            if (!home.isGeneral() && !mp.isOverriding()) {
                p.sendMessage("§cEsse home não é publica.");
                return;
            }

            if (factionAt != mp.getFaction() && !factionAt.isNone()) {
                p.sendMessage("§cVocê não pode teleportar para essa home.");
                return;
            }

            p.teleport(Utils.deserializeLocation(home.getLocation()), PlayerTeleportEvent.TeleportCause.COMMAND);
            p.sendMessage(String.format("§eTeleportado com sucesso a home '%s' do jogador %s.", home.getName(), split[0]));
            return;
        }

        if (!user.hasHome(name)) {
            p.sendMessage("§cVocê não possui essa home.");
            return;
        }

        Homes home = user.getHomes().get(name);
        Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(Utils.deserializeLocation(home.getLocation())));

        if (factionAt != mp.getFaction() && !factionAt.isNone()) {
            p.sendMessage("§cVocê não pode teleportar para essa home.");
            return;
        }

        p.teleport(Utils.deserializeLocation(home.getLocation()), PlayerTeleportEvent.TeleportCause.COMMAND);
        p.sendMessage(String.format("§eTeleportado com sucesso a home '%s'", home.getName()));
    }
}
