package com.heirteir.autoeye.player;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;
import com.heirteir.autoeye.player.data.*;
import com.heirteir.autoeye.util.vector.Vector3D;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedEntity;
import lombok.Getter;
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

    public AutoEyePlayer(Autoeye autoeye, Player player) {
        this.wrappedEntity = new WrappedEntity(autoeye, player);
        this.player = player;
        this.physics = new Physics(this);
        this.locationData = new LocationData(autoeye, this);
        this.infractionData = new InfractionData(autoeye);
        this.timeData = new TimeData();
        this.attackData = new AttackData();
        this.timeData.getConnected().update();
        this.connected = false;
    }

    public void update(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        this.locationData.update(autoeye, this, event.getPacket());
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

    public synchronized void teleport(Vector3D location) {
        this.getPlayer().teleport(new Location(this.player.getWorld(), location.getX(), location.getY(), location.getZ(), this.player.getEyeLocation().getYaw(), this.player.getEyeLocation().getPitch()));
    }
}
