package com.heirteir.autoeye;

import com.google.common.collect.Sets;
import com.heirteir.autoeye.event.events.EventHandler;
import com.heirteir.autoeye.event.packets.ChannelInjector;
import com.heirteir.autoeye.player.AutoEyePlayerList;
import com.heirteir.autoeye.util.MathUtil;
import com.heirteir.autoeye.util.TPS;
import com.heirteir.autoeye.util.logger.Logger;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.server.Version;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter public final class Autoeye extends JavaPlugin {
    private final Logger pluginLogger;
    private final Version version;
    private final Reflections reflections;
    private final ChannelInjector channelInjector;
    private final EventHandler eventHandler;
    private final AutoEyePlayerList autoEyePlayerList;
    private final MathUtil mathUtil;
    private final TPS tps;

    public Autoeye() {
        this.pluginLogger = new Logger(this);
        this.version = Version.getVersion(Bukkit.getBukkitVersion());
        if (!this.version.equals(Version.NONE)) {
            this.reflections = new Reflections(Bukkit.getServer().getClass().getPackage().getName());
            this.eventHandler = new EventHandler();
            this.autoEyePlayerList = new AutoEyePlayerList(this);
            this.channelInjector = new ChannelInjector();
            this.mathUtil = new MathUtil();
            this.tps = new TPS(this);
        } else {
            this.reflections = null;
            this.channelInjector = null;
            this.eventHandler = null;
            this.autoEyePlayerList = null;
            this.mathUtil = null;
            this.tps = null;
        }
    }

    @Override public void onEnable() {
        if (this.version.equals(Version.NONE)) {
            this.pluginLogger.sendConsoleMessageWithPrefix(this.pluginLogger.getPluginName() + " &cdoes not support the version of your Minecraft Server. " + this.pluginLogger.getPluginName() + " &conly supports &7[&e1.7-1.12&7]&c.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.tps.runTaskTimerAsynchronously(this, 100L, 1L);
        this.channelInjector.inject(this);
        this.autoEyePlayerList.createListener();
        this.eventHandler.createCheckEventExecutors(this);
    }

    @Override public void onDisable() {
        if (this.channelInjector != null) {
            for (Player player : Sets.newHashSet(Bukkit.getOnlinePlayers())) {
                this.channelInjector.removeChannel(player);
            }
        }
    }
}
