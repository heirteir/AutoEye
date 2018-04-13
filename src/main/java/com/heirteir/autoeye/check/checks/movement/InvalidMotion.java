package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;
import com.heirteir.autoeye.player.data.Physics;

public class InvalidMotion extends Check<PacketPlayInFlyingEvent> {
    public InvalidMotion() {
        super("Invalid Motion");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        if (event.getPlayer().getPhysics().getClientVelocity().getY() > event.getPlayer().getPhysics().getCalculatedYVelocity() + 0.001 && Math.abs(event.getPlayer().getPhysics().getClientAcceleration().getY() - event.getPlayer().getPhysics().getCalculatedYAcceleration()) > 0.001) {
            if (event.getPlayer().getPhysics().getClientVelocity().getY() == 0 && event.getPlayer().getTimeData().getLastTeleport().getDifference() < 1000) {
                return this.checkThreshold(event.getPlayer(), 5, 500L);
            }
            return !((event.getPlayer().getPhysics().getClientVelocity().getY() == 0) || (event.getPlayer().getPhysics().getCalculatedYVelocity() > 0 && event.getPlayer().getPhysics().getCalculatedYVelocity() < 0.04)) || this.checkThreshold(event.getPlayer(), 2, 100L);
        } else {
            System.out.println("2: " + event.getPlayer().getPhysics().getCalculatedYVelocity() + " " + event.getPlayer().getPhysics().getClientVelocity().getY() + " " + Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY() - event.getPlayer().getPhysics().getCalculatedYVelocity()));
            return Math.abs(event.getPlayer().getPhysics().getCalculatedYVelocity() - event.getPlayer().getPlayer().getVelocity().getY()) < 0.1 && Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY() - event.getPlayer().getPhysics().getCalculatedYVelocity()) > 0.001 && ((event.getPlayer().getPhysics().getCalculatedYVelocity() < 0 && (Math.abs(event.getPlayer().getPhysics().getClientAcceleration().getY() - event.getPlayer().getPhysics().getCalculatedYAcceleration()) > 0.001) || event.getPlayer().getPhysics().getCalculatedYVelocity() > 0)) && (!(event.getPlayer().getPhysics().getClientVelocity().getY() == Physics.GRAVITY) || this.checkThreshold(event.getPlayer(), 2, 100L));
        }
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return !event.getPlayer().getLocationData().isOnPiston() && !event.getPlayer().getLocationData().isOnSlime() && !event.getPlayer().getLocationData().isInWater() && !event.getPacket().isOnGround() && !event.getPlayer().getLocationData().isOnStairs() && !event.getPlayer().getLocationData().isOnLadder() && !(event.getPlayer().getPhysics().getClientVelocity().getY() == 0 && event.getPlayer().getLocationData().getSolidBlocks().getBlocks().size() > 0) && event.getPlayer().getTimeData().getLastInWeb().getDifference() > 150L;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
