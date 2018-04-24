/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.packets.channelhandler;

import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.api.AutoEyeInfractionEvent;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.checks.combat.KillAuraRotation;
import com.heirteir.autoeye.check.checks.combat.Reach;
import com.heirteir.autoeye.check.checks.movement.*;
import com.heirteir.autoeye.event.packets.PacketType;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInAbilities;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayOutEntityVelocity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import com.heirteir.autoeye.util.vector.Vector3D;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;
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
    //combat
    private final KillAuraRotation killAuraRotation;
    private final Reach reach;
    //movement
    private final Set<Check> movementChecks = Sets.newHashSet();
    //inventory
    private final InventoryWalk inventoryWalk;

    ChannelHandlerAbstract(Autoeye autoeye) {
        this.autoeye = autoeye;
        this.addChannelHandlerExecutor = Executors.newSingleThreadExecutor();
        this.removeChannelHandlerExecutor = Executors.newSingleThreadExecutor();
        this.handlerKey = "packet_handler";
        this.playerKey = "autoeye_player_handler";
        //combat
        this.killAuraRotation = new KillAuraRotation(this.autoeye);
        this.reach = new Reach(this.autoeye);
        //movement
        this.movementChecks.add(new FastLadder(this.autoeye));
        this.movementChecks.add(new InvalidLocation(this.autoeye));
        this.movementChecks.add(new InvalidMotion(this.autoeye));
        this.movementChecks.add(new NoWeb(this.autoeye));
        this.movementChecks.add(new SlimeJump(this.autoeye));
        this.movementChecks.add(new Speed(this.autoeye));
        this.movementChecks.add(new SpoofedOnGroundPacket(this.autoeye));
        this.movementChecks.add(new Step(this.autoeye));
        this.movementChecks.add(new Timer(this.autoeye));
        this.movementChecks.add(new NoSlowDown(this.autoeye));
        this.movementChecks.add(new InvalidPitch(this.autoeye));
        //inventory
        this.inventoryWalk = new InventoryWalk(this.autoeye);
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
                case NULL:
                    System.out.println(packet.getClass().getSimpleName());
                    break;
                case PacketPlayInWindowClick:
                    this.inventoryWalk.check(player);
                    break;
                case PacketPlayInFlying:
                    player.update(this.autoeye, new PacketPlayInFlying(packet));
                    for (Check check : this.movementChecks) {
                        if (check.check(player)) {
                            AutoEyeInfractionEvent e = new AutoEyeInfractionEvent(player.getPlayer(), player.getInfractionData().getInfraction(check));
                            Bukkit.getPluginManager().callEvent(e);
                            if (!e.isCancelled()) {
                                player.getInfractionData().addVL(player, check);
                                for (AutoEyePlayer p : this.autoeye.getAutoEyePlayerList().getPlayers().values()) {
                                    p.sendMessage(this.autoeye, this.autoeye.getPluginLogger().translateColorCodes(e.getMessage()));
                                }
                                return check.revert(player);
                            }
                        }
                    }
                    break;
                case PacketPlayInAbilities:
                    player.getPhysics().setFlying(new PacketPlayInAbilities(packet).isFlying());
                    break;
                case PacketPlayInUseEntity:
                    PacketPlayInUseEntity packetPlayInUseEntity = new PacketPlayInUseEntity(player.getPlayer().getWorld(), packet);
                    player.getAttackData().setLastEntity(packetPlayInUseEntity.getEntity());
                    player.getAttackData().setLastActionType(packetPlayInUseEntity.getActionType());
                    player.getTimeData().getLastUseEntity().update();
                    this.killAuraRotation.check(player);
                    this.reach.check(player);
                    break;
                case PacketPlayInKeepAlive:
                    player.getTimeData().getLastInKeepAlive().update();
                    player.setPing(player.getTimeData().getDifference(player.getTimeData().getLastOutKeepAlive().getTime(), player.getTimeData().getLastInKeepAlive().getTime()));
                    break;
                case PacketPlayOutKeepAlive:
                    player.getTimeData().getLastOutKeepAlive().update();
                    break;
                case PacketPlayOutPosition:
                    player.getTimeData().getLastTeleport().update();
                    player.getLocationData().setTeleported(true);
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

    public abstract void addChannel(Player player);

    public abstract void removeChannel(Player player);
}
