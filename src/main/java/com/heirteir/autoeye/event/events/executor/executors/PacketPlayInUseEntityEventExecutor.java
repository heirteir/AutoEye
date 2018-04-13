package com.heirteir.autoeye.event.events.executor.executors;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.checks.combat.KillAuraRotation;
import com.heirteir.autoeye.check.checks.combat.Reach;
import com.heirteir.autoeye.event.events.event.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.event.events.executor.CheckEventExecutor;
import lombok.Getter;

@Getter public class PacketPlayInUseEntityEventExecutor extends CheckEventExecutor<PacketPlayInUseEntityEvent> {
    public PacketPlayInUseEntityEventExecutor(Autoeye autoeye) {
        super(autoeye);
        this.checks.add(new KillAuraRotation());
        this.checks.add(new Reach());
    }

    @Override public void run(PacketPlayInUseEntityEvent event) {
        event.getPlayer().getAttackData().setLastEntity(event.getPacket().getEntity());
        event.getPlayer().getAttackData().setLastActionType(event.getPacket().getActionType());
        event.getPlayer().getTimeData().getLastUseEntity().update();
        this.bulkRunChecks(event);
    }
}
