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
import com.heirteir.autoeye.util.logger.Logger;
import com.heirteir.autoeye.util.server.Version;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter public final class Autoeye extends JavaPlugin {
    @Getter private static final Version version = Version.getVersion(Bukkit.getBukkitVersion());
    private final Logger pluginLogger;
    private final ChannelInjector channelInjector;
    private final EventHandler eventHandler;
    private final AutoEyePlayerList autoEyePlayerList;
    private final MathUtil mathUtil;
    private final PermissionsManager permissionsManager;
    private boolean running = false;

    public Autoeye() {
        this.pluginLogger = new Logger(this);
        if (!version.equals(Version.NONE)) {
            this.eventHandler = new EventHandler(this);
            this.autoEyePlayerList = new AutoEyePlayerList(this);
            this.channelInjector = new ChannelInjector();
            this.mathUtil = new MathUtil();
            this.permissionsManager = new PermissionsManager();
        } else {
            this.channelInjector = null;
            this.eventHandler = null;
            this.autoEyePlayerList = null;
            this.mathUtil = null;
            this.permissionsManager = null;
        }
    }

    @Override public void onEnable() {
        if (version.equals(Version.NONE)) {
            this.pluginLogger.sendConsoleMessageWithPrefix(this.pluginLogger.getPluginName() + "&c does not support the version of your Minecraft Server. " + this.pluginLogger.getPluginName() + " &conly supports &7[&e1.7-1.12&7]&c.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.running = true;
        this.channelInjector.inject(this);
        this.autoEyePlayerList.createListener();
        this.eventHandler.createCheckEventExecutors(this);
    }

    @Override public void onDisable() {
        if (this.autoEyePlayerList != null) {
            this.autoEyePlayerList.unregister();
        }
        this.running = false;
    }
}
