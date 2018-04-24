package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class InventoryWalk extends Check {
    public InventoryWalk(Autoeye autoeye) {
        super(autoeye, "Inventory Walk");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return false;
    }
    //    public boolean check(PlayerMoveEvent event) {
    //        return event.getPacket().isHasPos() && player.getPhysics().isMoving() && player.getPlayer().getVelocity().getX() == 0 && player.getPlayer().getVelocity().getZ() == 0 && player.getPlayer().getVelocity().getY() > -0.1F && !player.getPlayer().getOpenInventory().getType().equals(InventoryType.CRAFTING) && this.checkThreshold(player, 4, 100L);
    //    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
