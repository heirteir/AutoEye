package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.entity.Player;

public class Reach extends Check {
    public Reach(Autoeye autoeye) {
        super(autoeye, "Reach (Impossible Distance)");
    }

    @EventExecutor public boolean check(PacketPlayInUseEntityEvent event) {
        if (event.getPacket().getEntity() != null && event.getPacket().getActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && (event.getPacket().getEntity() instanceof Player)) {
            AutoEyePlayer attacker = event.getPlayer();
            AutoEyePlayer attacked = autoeye.getAutoEyePlayerList().getPlayer((Player) event.getPacket().getEntity());
            float distance = (float) Math.sqrt(Math.pow(attacked.getLocationData().getLocation().getX() - attacker.getLocationData().getLocation().getX(), 2) + Math.pow(attacked.getLocationData().getLocation().getZ() - attacker.getLocationData().getLocation().getZ(), 2)) + ((attacker.getPhysics().getCalculatedYVelocity() > 0.f || attacked.getPhysics().getCalculatedYVelocity() > 0) ? 0.9F : 0);
            float allowedDistance = 3.4F + (float) this.getPingFormula((double) attacker.getTimeData().getLastKeepAlive().getDifference(), (double) attacked.getTimeData().getLastKeepAlive().getDifference());
            attacker.getAttackData().updateReachBuffer(distance);
            if (distance >= 5F) {
                return true;
            } else {
                for (float value : attacker.getAttackData().getReachBuffer()) {
                    if (value <= allowedDistance) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override public <T extends Event> void revert(T event) {
    }

    private double getPingFormula(double attackerPing, double attackedPing) {
        double removedDistance = Math.min(0.0, (attackerPing + attackedPing / 2) * 0.0017000000225380063);
        return removedDistance > 0.0 ? removedDistance : 0.d;
    }
}
