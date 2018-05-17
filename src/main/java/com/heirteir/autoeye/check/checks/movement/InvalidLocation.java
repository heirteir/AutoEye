package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.util.NumberConversions;

public class InvalidLocation extends Check {
    public InvalidLocation(Autoeye autoeye) {
        super(autoeye, CheckType.PRE_MOVE_EVENT, "Invalid Location");
    }

    @Override public boolean check(AutoEyePlayer player) {
        try {
            player.getPlayer().getWorld().getBlockAt(NumberConversions.floor(player.getLocationData().getLocation().getX()), NumberConversions.floor(player.getLocationData().getLocation().getY()), NumberConversions.floor(player.getLocationData().getLocation().getZ()));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
