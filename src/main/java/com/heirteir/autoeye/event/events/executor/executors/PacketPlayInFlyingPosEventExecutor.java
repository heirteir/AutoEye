package com.heirteir.autoeye.event.events.executor.executors;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.checks.movement.*;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;
import com.heirteir.autoeye.event.events.executor.CheckEventExecutor;

public class PacketPlayInFlyingPosEventExecutor extends CheckEventExecutor<PacketPlayInFlyingEvent> {
    public PacketPlayInFlyingPosEventExecutor(Autoeye autoeye) {
        super(autoeye);
        this.checks.add(new FastLadder());
        this.checks.add(new InvalidMotion());
        this.checks.add(new NoWeb());
        this.checks.add(new SlimeJump());
        this.checks.add(new SpoofedOnGroundPacket());
        this.checks.add(new Step());
        this.checks.add(new Timer());
        this.checks.add(new Velocity());
    }

    @Override public void run(PacketPlayInFlyingEvent event) {
        event.getPlayer().update(this.autoeye, event);
        if (event.getPlayer().isConnected() && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported()) {
            this.bulkRunChecks(event);
            event.getPlayer().getPhysics().setHasVelocity(false);
        }
        event.getPlayer().getTimeData().getLastPacketPlayInFlying().update();
    }
}
