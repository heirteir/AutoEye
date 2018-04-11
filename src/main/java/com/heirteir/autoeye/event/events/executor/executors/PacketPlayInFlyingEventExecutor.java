package com.heirteir.autoeye.event.events.executor.executors;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.checks.movement.*;
import com.heirteir.autoeye.event.events.PacketPlayInFlyingEvent;
import com.heirteir.autoeye.event.events.executor.CheckEventExecutor;

public class PacketPlayInFlyingEventExecutor extends CheckEventExecutor<PacketPlayInFlyingEvent> {
    public PacketPlayInFlyingEventExecutor(Autoeye autoeye) {
        super(autoeye);
        this.checks.add(new FastLadder());
        this.checks.add(new InvalidMotion());
        this.checks.add(new NoWeb());
        this.checks.add(new SlimeJump());
        this.checks.add(new SpoofedOnGroundPacket());
        this.checks.add(new Step());
    }

    @Override public void run(PacketPlayInFlyingEvent event) {
        event.getPlayer().update(this.autoeye, event);
        if (event.getPacket().isChild() && event.getPacket().isHasPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported()) {
            this.bulkRunChecks(event);
        }
    }
}
