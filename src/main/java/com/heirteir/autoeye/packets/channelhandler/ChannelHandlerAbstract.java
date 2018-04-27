/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.packets.channelhandler;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.api.AutoEyeInfractionEvent;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.packets.PacketType;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInAbilities;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.packets.wrappers.PacketPlayOutEntityVelocity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import com.heirteir.autoeye.util.vector.Vector3D;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class ChannelHandlerAbstract {
    static final WrappedField networkManagerField = Reflections.getNMSClass("PlayerConnection").getFieldByName("networkManager");
    static final WrappedField playerConnectionField = Reflections.getNMSClass("EntityPlayer").getFieldByName("playerConnection");
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
            switch (PacketType.fromString(packet.getClass().getSimpleName())) {
                case PacketPlayInWindowClick:
                    return this.runChecks(player, CheckType.INVENTORY_EVENT);
                case PacketPlayInFlying:
                    PacketPlayInFlying packetPlayInFlying = new PacketPlayInFlying(packet);
                    if (packetPlayInFlying.isHasPos()) {
                        player.getLocationData().setLocation(new Vector3D(packetPlayInFlying.getX(), packetPlayInFlying.getY(), packetPlayInFlying.getZ()));
                    }
                    if (!this.runChecks(player, CheckType.PRE_MOVE_EVENT)) {
                        return false;
                    }
                    player.update(this.autoeye, packetPlayInFlying);
                    return this.runChecks(player, CheckType.MOVE_EVENT);
                case PacketPlayInAbilities:
                    player.getPhysics().setFlying(new PacketPlayInAbilities(packet).isFlying());
                    break;
                case PacketPlayInUseEntity:
                    PacketPlayInUseEntity packetPlayInUseEntity = new PacketPlayInUseEntity(player.getPlayer().getWorld(), packet);
                    player.getInteractData().setLastEntity(packetPlayInUseEntity.getEntity());
                    player.getInteractData().setLastActionType(packetPlayInUseEntity.getActionType());
                    player.getTimeData().getLastUseEntity().update();
                    return this.runChecks(player, CheckType.ENTITY_USE_EVENT);
                case PacketPlayInKeepAlive:
                    player.getTimeData().getLastInKeepAlive().update();
                    player.setPing(player.getTimeData().getDifference(player.getTimeData().getLastOutKeepAlive().getTime(), player.getTimeData().getLastInKeepAlive().getTime()));
                    player.getInteractData().setHitsSinceLastAlivePacket(0);
                    break;
                case PacketPlayOutKeepAlive:
                    player.getTimeData().getLastOutKeepAlive().update();
                    break;
                case PacketPlayOutPosition:
                    player.getLocationData().setTeleported(player, true);
                    player.getLocationData().reset(this.autoeye, player);
                    player.getPhysics().reset(player);
                    break;
                case PacketPlayOutEntityVelocity:
                    PacketPlayOutEntityVelocity packetPlayOutEntityVelocity = new PacketPlayOutEntityVelocity(player, packet);
                    if (packetPlayOutEntityVelocity.isPlayer()) {
                        player.getPhysics().setServerVelocity(new Vector3D(packetPlayOutEntityVelocity.getX(), packetPlayOutEntityVelocity.getY(), packetPlayOutEntityVelocity.getZ()));
                        if (packetPlayOutEntityVelocity.getY() < -0.0784F || packetPlayOutEntityVelocity.getY() > 0) {
                            player.getPhysics().setHasVelocity(true);
                            player.getTimeData().getLastVelocity().update();
                        }
                    }
                    break;
            }
        }
        return true;
    }

    private boolean runChecks(AutoEyePlayer player, CheckType type) {
        for (Check check : this.autoeye.getCheckRegister().getCheckList(type)) {
            if (check.isEnabled() && !player.getPlayer().hasPermission(check.getPermission()) && check.check(player)) {
                AutoEyeInfractionEvent e = new AutoEyeInfractionEvent(player.getPlayer(), player.getInfractionData().getInfraction(check));
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled()) {
                    player.getInfractionData().addVL(player, check);
                    for (AutoEyePlayer p : this.autoeye.getAutoEyePlayerList().getPlayers().values()) {
                        p.sendMessage(this.autoeye, this.autoeye.getPluginLogger().translateColorCodes(e.getMessage()));
                    }
                    if (check.isRevert()) {
                        return check.revert(player);
                    }
                }
                return true;
            }
        }
        return true;
    }

    public abstract void addChannel(Player player);

    public abstract void removeChannel(Player player);
}
