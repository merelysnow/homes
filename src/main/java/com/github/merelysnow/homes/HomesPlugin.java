package com.github.merelysnow.homes;

import com.github.merelysnow.homes.commands.*;
import com.github.merelysnow.homes.database.connection.RepositoryProvider;
import com.github.merelysnow.homes.listeners.PlayerListeners;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.plugin.java.JavaPlugin;

public class HomesPlugin extends JavaPlugin {

    public static HomesPlugin plugin;
    @Getter
    private SQLConnector CONNECTOR;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;

        CONNECTOR = RepositoryProvider.of(this).prepare();
        new HomesRepositories();

        registerEvents();
        registerCommands();
    }

    private void registerCommands() {
        BukkitFrame bukkitFrame = new BukkitFrame(this);

        bukkitFrame.registerCommands(
                new SetHomeCommand(),
                new HomeCommand(),
                new DelHomeCommand(),
                new PublicCommand(),
                new PrivateCommand(),
                new HomesCommand()
        );


        MessageHolder messageHolder = bukkitFrame.getMessageHolder();

        messageHolder.setMessage(MessageType.NO_PERMISSION, "§cVocê não tem permissão para executar este comando.");
        messageHolder.setMessage(MessageType.ERROR, "§cUm erro ocorreu! {error}");
        messageHolder.setMessage(MessageType.INCORRECT_USAGE, "§cUtilize /{usage}");
        messageHolder.setMessage(MessageType.INCORRECT_TARGET, "§cVocê não pode utilizar este comando pois ele é direcioado apenas para {target}.");
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
    }
}
