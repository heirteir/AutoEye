package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class InventoryWalk extends Check {
    public InventoryWalk(Autoeye autoeye) {
        super(autoeye, CheckType.INVENTORY_EVENT, "Inventory Walk");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return player.getLocationData().isChangedPos() && !player.getPhysics().isFlying() && player.getPhysics().isMoving() && player.getPhysics().getServerVelocity().getX() == 0 && player.getPhysics().getServerVelocity().getZ() == 0 && player.getTimeData().getLastMove().getDifference() < 60L;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.getPlayer().closeInventory();
        return false;
    }
}
