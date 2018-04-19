/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.event.packets.channelhandler;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.*;
import com.heirteir.autoeye.event.packets.PacketType;
import com.heirteir.autoeye.event.packets.wrappers.*;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import org.bukkit.entity.Player;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class ChannelHandlerAbstract {
    protected final Autoeye autoeye;
    final Executor addChannelHandlerExecutor;
    final Executor removeChannelHandlerExecutor;
    final WrappedField networkManagerField;
    final WrappedField playerConnectionField;
    final String handlerKey;
    final String playerKey;

    ChannelHandlerAbstract(Autoeye autoeye) {
        this.autoeye = autoeye;
        this.addChannelHandlerExecutor = Executors.newSingleThreadExecutor();
        this.removeChannelHandlerExecutor = Executors.newSingleThreadExecutor();
        this.networkManagerField = autoeye.getReflections().getNMSClass("PlayerConnection").getFieldByName("networkManager");
        this.playerConnectionField = autoeye.getReflections().getNMSClass("EntityPlayer").getFieldByName("playerConnection");
        this.handlerKey = "packet_handler";
        this.playerKey = "autoeye_player_handler";
    }

    public void run(AutoEyePlayer player, Object packet) {
        if (this.autoeye.isEnabled() && packet != null && player != null && player.getPlayer() != null && player.getPlayer().isOnline()) {
            Event event = null;
            switch (PacketType.fromString(packet.getClass().getSimpleName())) {
                case NULL:
                    break;
                case PacketPlayInFlying:
                    event = new PlayerMoveEvent(player, new PacketPlayInFlying(this.autoeye, packet, !packet.getClass().getSimpleName().equals("PacketPlayInFlying")));
                    break;
                case PacketPlayInAbilities:
                    event = new PacketPlayInAbilitiesEvent(player, new PacketPlayInAbilities(this.autoeye, packet));
                    break;
                case PacketPlayInUseEntity:
                    event = new PacketPlayInUseEntityEvent(player, new PacketPlayInUseEntity(this.autoeye, player.getPlayer().getWorld(), packet));
                    break;
                case PacketPlayInBlockPlace:
                    event = new BlockPlaceEvent(player, new PacketPlayInBlockPlace(this.autoeye, player.getPlayer().getWorld(), packet));
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
                    PacketPlayOutEntityVelocity packetPlayOutEntityVelocity = new PacketPlayOutEntityVelocity(this.autoeye, player, packet);
                    if (packetPlayOutEntityVelocity.isPlayer()) {
                        event = new PlayerVelocityEvent(player, packetPlayOutEntityVelocity);
                    }
                    break;
            }
            if (event != null) {
                this.autoeye.getEventHandler().run(event);
            }
        }
    }

    public abstract void addChannel(Player player);

    public abstract void removeChannel(Player player);
}
