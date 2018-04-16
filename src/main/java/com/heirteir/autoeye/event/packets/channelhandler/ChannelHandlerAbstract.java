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

    protected void packetReceived(AutoEyePlayer player, Object packet) {
        if (player == null || packet == null) {
            return;
        }
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
        }
        if (event != null) {
            this.autoeye.getEventHandler().run(event);
        }
    }

    protected void packetSent(AutoEyePlayer player, Object packet) {
        if (player == null || packet == null) {
            return;
        }
        Event event = null;
        switch (PacketType.fromString(packet.getClass().getSimpleName())) {
            case NULL:
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

    public abstract void addChannel(Player player);

    public abstract void removeChannel(Player player);
}
