/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye;

import com.heirteir.autoeye.event.events.EventHandler;
import com.heirteir.autoeye.event.packets.ChannelInjector;
import com.heirteir.autoeye.permissions.PermissionsManager;
import com.heirteir.autoeye.player.AutoEyePlayerList;
import com.heirteir.autoeye.util.MathUtil;
import com.heirteir.autoeye.util.TPS;
import com.heirteir.autoeye.util.logger.Logger;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.server.Version;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

@Getter public final class Autoeye extends JavaPlugin {
    private final Logger pluginLogger;
    private final Version version;
    private final Reflections reflections;
    private final ChannelInjector channelInjector;
    private final EventHandler eventHandler;
    private final AutoEyePlayerList autoEyePlayerList;
    private final MathUtil mathUtil;
    private final PermissionsManager permissionsManager;
    private final TPS tps;

    public Autoeye() {
        this.pluginLogger = new Logger(this);
        this.version = Version.getVersion(Bukkit.getBukkitVersion());
        if (!this.version.equals(Version.NONE)) {
            this.reflections = new Reflections(Bukkit.getServer().getClass().getPackage().getName());
            this.eventHandler = new EventHandler(this);
            this.autoEyePlayerList = new AutoEyePlayerList(this);
            this.channelInjector = new ChannelInjector();
            this.mathUtil = new MathUtil();
            this.permissionsManager = new PermissionsManager();
            this.tps = new TPS(this);
        } else {
            this.reflections = null;
            this.channelInjector = null;
            this.eventHandler = null;
            this.autoEyePlayerList = null;
            this.mathUtil = null;
            this.permissionsManager = null;
            this.tps = null;
        }
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ((Player) sender).setVelocity(new Vector(5, 5, 0));
        return super.onCommand(sender, command, label, args);
    }

    @Override public void onEnable() {
        if (this.version.equals(Version.NONE)) {
            this.pluginLogger.sendConsoleMessageWithPrefix(this.pluginLogger.getPluginName() + "&c does not support the version of your Minecraft Server. " + this.pluginLogger.getPluginName() + " &conly supports &7[&e1.7-1.12&7]&c.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.tps.runTaskTimerAsynchronously(this, 100L, 1L);
        this.channelInjector.inject(this);
        this.autoEyePlayerList.createListener();
        this.eventHandler.createCheckEventExecutors(this);
    }
}
