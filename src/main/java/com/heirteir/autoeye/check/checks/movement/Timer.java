package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;

public class Timer extends Check {
    public Timer(Autoeye autoeye) {
        super(autoeye, "Timer");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        return event.getPlayer().isConnected() && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && event.getPlayer().getPhysics().getMovesPerSecond() > (22F + (20F - this.autoeye.getTps().get()));
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
