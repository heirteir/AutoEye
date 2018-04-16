package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Reach extends Check {

    public Reach(Autoeye autoeye) {
        super(autoeye, "Reach (Impossible Distance)");
    }

    @EventExecutor(event = PacketPlayInUseEntityEvent.class)
    public boolean check(PacketPlayInUseEntityEvent event) {

        if (!(event.getPacket().getEntity() instanceof Player || event.getPacket().getEntity() == null))
            return true;

        AutoEyePlayer attacker = event.getPlayer();
        AutoEyePlayer attacked = autoeye.getAutoEyePlayerList().getPlayer((Player) event.getPacket().getEntity());

        double deltaX = attacked.getLocationData().getLocation().getX() - attacker.getLocationData().getLocation().getX(),
                deltaZ = attacked.getLocationData().getLocation().getZ() - attacker.getLocationData().getLocation().getZ();
        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double allowedDistance = 3.100001f;

        double attackerPing = attacker.getTimeData().getLastKeepAlive().getDifference();
        double attackedPing = attacked.getTimeData().getLastKeepAlive().getDifference();

        double attackerVelY = attacker.getPhysics().getCalculatedYVelocity(),
                attackedVelY = attacked.getPhysics().getCalculatedYVelocity();

        if (attackerVelY > 0.d || attackedVelY > 0.0)
            distance += 0.9;

        allowedDistance += this.getPingFormula(attackerPing, attackedPing);

        // HA NO FALSES TODAY
        if (distance >= 5.0)
            return true;

        if (Arrays.stream(attacker.getAttackData().reachBuffer).noneMatch(d -> d >= 0.d)) {
            if (attacker.getAttackData().reachBuffer[0] >= allowedDistance
                    && attacker.getAttackData().reachBuffer[1] >= allowedDistance
                    && attacker.getAttackData().reachBuffer[2] >= allowedDistance
                    && attacker.getAttackData().reachBuffer[3] >= allowedDistance
                    && attacker.getAttackData().reachBuffer[4] >= allowedDistance) {
                return this.checkThreshold(attacker, 1);
            }
        }

        attacker.getAttackData().reachBuffer[4] = attacker.getAttackData().reachBuffer[3];
        attacker.getAttackData().reachBuffer[3] = attacker.getAttackData().reachBuffer[2];
        attacker.getAttackData().reachBuffer[2] = attacker.getAttackData().reachBuffer[1];
        attacker.getAttackData().reachBuffer[1] = attacker.getAttackData().reachBuffer[0];
        attacker.getAttackData().reachBuffer[0] = distance;

        return false;
    }

    @Override public <T extends Event> void revert(T event) {
    }

    private double getPingFormula(double attackerPing, double attackedPing) {
        double removedDistance = Math.min(0.0, (attackerPing + attackedPing / 2) * 0.0017000000225380063);

        return removedDistance > 0.0 ? removedDistance : 0.d;
    }
}
