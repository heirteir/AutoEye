package com.heirteir.autoeye.event.events.executor.executors;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.PlayerTeleportEvent;
import com.heirteir.autoeye.event.events.executor.EventExecutor;

public class PlayerTeleportEventExecutor extends EventExecutor<PlayerTeleportEvent> {
    public PlayerTeleportEventExecutor(Autoeye autoeye) {
        super(autoeye);
    }

    @Override public void run(PlayerTeleportEvent event) {
        event.getPlayer().getLocationData().reset(this.autoeye, event.getPlayer());
        event.getPlayer().getPhysics().reset(event.getPlayer());
        event.getPlayer().getTimeData().getLastTeleport().update();
        event.getPlayer().getLocationData().setTeleported(true);
    }
}
