package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInUseEntityEvent;

public class Reach extends Check<PacketPlayInUseEntityEvent> {
    public Reach() {
        super("Reach");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
        return false;
    }

    @Override public boolean canRun(PacketPlayInUseEntityEvent event) {
        return event.getPacket().getEntity() != null;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
    }
}
