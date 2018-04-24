/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 8:05 PM
 */
package com.heirteir.autoeye.player;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.player.data.*;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedEntity;
import com.heirteir.autoeye.util.vector.Vector3D;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Map;

@Getter public class AutoEyePlayer {
    private final WrappedEntity wrappedEntity;
    private final Player player;
    private final Physics physics;
    private final LocationData locationData;
    private final InfractionData infractionData;
    private final TimeData timeData;
    private final AttackData attackData;
    private boolean connected;
    @Setter public long ping = 0;

    public AutoEyePlayer(Autoeye autoeye, Player player) {
        this.wrappedEntity = new WrappedEntity(autoeye, player);
        this.player = player;
        this.physics = new Physics(this);
        this.locationData = new LocationData(autoeye, this);
        this.infractionData = new InfractionData();
        this.timeData = new TimeData();
        this.attackData = new AttackData();
        this.timeData.getConnected().update();
        this.connected = false;
    }

    public void update(Autoeye autoeye, PacketPlayInFlying packet) {
        this.locationData.update(autoeye, this, packet);
        this.physics.update(this);
        this.connected = this.connected || this.timeData.getConnected().getDifference() > 2000;
    }

    public int getPotionEffectAmplifier(String name) {
        for (PotionEffect e : this.player.getActivePotionEffects()) {
            if (e.getType().getName().equals(name)) {
                return e.getAmplifier() + 1;
            }
        }
        return 0;
    }

    public int getEnchantmentEffectAmplifier(String name) {
        for (ItemStack item : this.player.getInventory().getArmorContents()) {
            if (item != null) {
                for (Map.Entry<Enchantment, Integer> enchantment : item.getEnchantments().entrySet()) {
                    if (enchantment.getKey().getName().equals(name)) {
                        return enchantment.getValue();
                    }
                }
            }
        }
        return 0;
    }

    public synchronized void sendMessage(Autoeye autoeye, String message) {
        if (this.player.hasPermission(autoeye.getPermissionsManager().getNotify())) {
            this.player.sendMessage(message);
        }
    }

    public synchronized void teleport(Vector3D location) {
        this.getPlayer().teleport(new Location(this.player.getWorld(), location.getX(), location.getY(), location.getZ(), this.player.getEyeLocation().getYaw(), this.player.getEyeLocation().getPitch()));
    }
}
