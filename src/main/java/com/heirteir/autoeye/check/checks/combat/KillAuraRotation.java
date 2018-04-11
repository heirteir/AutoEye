package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedAxisAlignedBB;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class KillAuraRotation extends Check<PacketPlayInUseEntityEvent> {
    public KillAuraRotation() {
        super("Kill Aura (Rotation)");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
        if (Math.abs(Math.abs(yawToLocation(autoeye, event.getPlayer().getPlayer(), event.getPacket().getEntity())) - Math.abs(clampYaw(degreesToRadians(event.getPlayer().getPlayer().getEyeLocation().getYaw())))) <= Math.abs(event.getPlayer().getLocationData().getYawDiff())) {
            return this.checkThreshold(event.getPlayer(), 3);
        } else {
            return this.resetThreshold(event.getPlayer());
        }
    }

    @Override public boolean canRun(PacketPlayInUseEntityEvent event) {
        return true;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
    }

    private float yawToLocation(Autoeye autoeye, Player player, Entity entity) {
        WrappedAxisAlignedBB bb = new WrappedAxisAlignedBB(autoeye, entity);
        return (float) Math.atan2(((bb.get("c") + bb.get("f")) / 2F) - player.getEyeLocation().getZ(), ((bb.get("a") + bb.get("d")) / 2F) - player.getEyeLocation().getX());
    }

    private float degreesToRadians(float yaw) {
        return (float) (((yaw + 90F) * Math.PI) / 180F);
    }

    private float clampYaw(float dub) {
        dub %= 2 * Math.PI;
        if (dub >= Math.PI) {
            dub -= 2 * Math.PI;
        }
        if (dub < -Math.PI) {
            dub += 2 * Math.PI;
        }
        return dub;
    }
}
