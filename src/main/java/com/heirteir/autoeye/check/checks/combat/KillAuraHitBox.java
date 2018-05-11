package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedEntity;
import com.heirteir.autoeye.util.vector.Ray3D;
import org.bukkit.entity.LivingEntity;

public class KillAuraHitBox extends Check {
    public KillAuraHitBox(Autoeye autoeye) {
        super(autoeye, CheckType.ENTITY_USE_EVENT, "Kill Aura (Hit Box)");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return (player.getInteractData().getLastEntity() != null && player.getInteractData().getLastActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && new WrappedEntity(autoeye, (LivingEntity) player.getInteractData().getLastEntity().getBukkitEntity()).getAxisAlignedBB().intersectsRay(new Ray3D(player.getLocationData().getLocation().offset(0, (float) player.getPlayer().getEyeHeight(), 0), player.getLocationData().getDirection().toVector3D())) == null) ? this.checkThreshold(player, 10) : this.resetThreshold(player);
    }

    @Override public boolean revert(AutoEyePlayer player) {
        return true;
    }
}
