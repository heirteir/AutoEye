package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class SlimeJump extends Check {
    public SlimeJump(Autoeye autoeye) {
        super(autoeye, CheckType.MOVE_EVENT, "Slime Jump");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (!player.getPhysics().isFlying() && player.getLocationData().isOnSlime() && !player.getLocationData().isCurrentlyOnSlime() && Math.abs(player.getPhysics().getServerYAcceleration()) < 0.087) {
            if ((player.getPhysics().getClientVelocity().getY() > player.getPhysics().getServerYVelocity() || Math.abs(player.getPhysics().getClientVelocity().getY() - player.getPhysics().getServerYVelocity()) > 0.2) && Math.abs(player.getPhysics().getClientAcceleration().getY() - player.getPhysics().getServerYAcceleration()) > 0.013 && Math.abs(player.getPhysics().getClientVelocity().getY() - player.getPhysics().getCalculatedYVelocity()) > 0.008) {
                return player.getPhysics().getClientVelocity().getY() > player.getPhysics().getServerYVelocity() * 3 || this.checkThreshold(player, 2, 500L);
            }
        }
        return false;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
