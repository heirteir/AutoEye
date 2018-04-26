package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.Location;

public class InvalidPitch extends Check {
    public InvalidPitch(Autoeye autoeye) {
        super(autoeye, CheckType.MOVE_EVENT, "Invalid Pitch");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return player.getLocationData().isChangedLook() && Math.abs(player.getLocationData().getDirection().getY()) > 90;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.getPlayer().teleport(new Location(player.getPlayer().getWorld(), player.getLocationData().getLocation().getX(), player.getLocationData().getLocation().getY(), player.getLocationData().getLocation().getZ(), player.getPlayer().getPlayer().getLocation().getYaw(), 0F));
        return true;
    }
}
