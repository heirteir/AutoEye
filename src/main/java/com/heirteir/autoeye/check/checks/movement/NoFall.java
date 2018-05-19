package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class NoFall extends Check {
    public NoFall(Autoeye autoeye) {
        super(autoeye, CheckType.MOVE_EVENT, "No Fall");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return player.isConnected() && !player.getLocationData().isClientOnGround() && player.getPhysics().getClientVelocity().getY() < 0 && !player.getLocationData().isOnLadder() && !player.getLocationData().isInWeb() && !player.getLocationData().isOnPiston() && Math.abs(player.getPhysics().getPreviousClientFallDistance() - player.getPlayer().getFallDistance()) > 0.2 * player.getPhysics().getOffGroundTicks() && this.checkThreshold(player, 3, 500L);
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
