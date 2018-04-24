package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.util.NumberConversions;

public class InvalidLocation extends Check {
    public InvalidLocation(Autoeye autoeye) {
        super(autoeye, "Invalid Location");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getLocationData().isChangedPos()) {
            try {
                player.getPlayer().getWorld().getBlockAt(NumberConversions.floor(player.getLocationData().getLocation().getX()), NumberConversions.floor(player.getLocationData().getLocation().getY()), NumberConversions.floor(player.getLocationData().getLocation().getZ()));
            } catch (IllegalStateException e) {
                return true;
            }
        }
        return false;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
