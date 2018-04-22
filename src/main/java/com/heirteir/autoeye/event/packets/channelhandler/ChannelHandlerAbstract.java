/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.packets.channelhandler;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.*;
import com.heirteir.autoeye.event.packets.PacketType;
import com.heirteir.autoeye.event.packets.wrappers.*;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class ChannelHandlerAbstract {
    protected static final WrappedField networkManagerField = Reflections.getNMSClass("PlayerConnection").getFieldByName("networkManager");
    protected static final WrappedField playerConnectionField = Reflections.getNMSClass("EntityPlayer").getFieldByName("playerConnection");
    protected final Autoeye autoeye;
    final Executor addChannelHandlerExecutor;
    final Executor removeChannelHandlerExecutor;
    final String handlerKey;
    final String playerKey;

    ChannelHandlerAbstract(Autoeye autoeye) {
        this.autoeye = autoeye;
        this.addChannelHandlerExecutor = Executors.newSingleThreadExecutor();
        this.removeChannelHandlerExecutor = Executors.newSingleThreadExecutor();
        this.handlerKey = "packet_handler";
        this.playerKey = "autoeye_player_handler";
    }

    public boolean run(AutoEyePlayer player, Object packet) {
        if (!this.autoeye.isRunning()) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin("Autoeye");
            if (plugin != null && plugin.isEnabled()) {
                Object channelInjector = Reflections.getClass(plugin.getClass()).getMethod("getChannelInjector").invoke(plugin);
                Reflections.getClass(channelInjector.getClass()).getMethod("addChannel", Player.class).invoke(channelInjector, player.getPlayer());
            }
            return true;
        }
        if (this.autoeye.isEnabled() && packet != null && player != null && player.getPlayer() != null && player.getPlayer().isOnline()) {
            Event event = null;
            switch (PacketType.fromString(packet.getClass().getSimpleName())) {
                case NULL:
                    break;
                case PacketPlayInFlying:
                    event = new PlayerMoveEvent(player, new PacketPlayInFlying(packet));
                    break;
                case PacketPlayInAbilities:
                    event = new PacketPlayInAbilitiesEvent(player, new PacketPlayInAbilities(packet));
                    break;
                case PacketPlayInUseEntity:
                    event = new PacketPlayInUseEntityEvent(player, new PacketPlayInUseEntity(player.getPlayer().getWorld(), packet));
                    break;
                case PacketPlayInBlockPlace:
                    event = new BlockPlaceEvent(player, new PacketPlayInBlockPlace(player.getPlayer().getWorld(), packet));
                    break;
                case PacketPlayInKeepAlive:
                    player.getTimeData().getLastInKeepAlive().update();
                    player.setPing(player.getTimeData().getDifference(player.getTimeData().getLastOutKeepAlive().getTime(), player.getTimeData().getLastInKeepAlive().getTime()));
                    break;
                case PacketPlayOutKeepAlive:
                    player.getTimeData().getLastOutKeepAlive().update();
                    break;
                case PacketPlayOutPosition:
                    event = new PlayerTeleportEvent(player);
                    break;
                case PacketPlayOutEntityVelocity:
                    PacketPlayOutEntityVelocity packetPlayOutEntityVelocity = new PacketPlayOutEntityVelocity(player, packet);
                    if (packetPlayOutEntityVelocity.isPlayer()) {
                        event = new PlayerVelocityEvent(player, packetPlayOutEntityVelocity);
                    }
                    break;
            }
            if (event != null) {
                return this.autoeye.getEventHandler().run(event);
            }
        }
        return true;
    }

    public abstract void addChannel(Player player);

    public abstract void removeChannel(Player player);
}
