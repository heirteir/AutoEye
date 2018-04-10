package com.heirteir.autoeye.event.events.executor.executors;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.checks.combat.Reach;
import com.heirteir.autoeye.event.events.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.event.events.executor.CheckEventExecutor;
import lombok.Getter;

@Getter public class PacketPlayInUseEntityEventExecutor extends CheckEventExecutor<PacketPlayInUseEntityEvent> {
    public PacketPlayInUseEntityEventExecutor(Autoeye autoeye) {
        super(autoeye);
        this.checks.add(new Reach());
    }

    @Override public void run(PacketPlayInUseEntityEvent event) {
        this.bulkRunChecks(event);
    }
}
