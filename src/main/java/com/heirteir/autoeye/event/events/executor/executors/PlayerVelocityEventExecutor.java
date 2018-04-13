package com.heirteir.autoeye.event.events.executor.executors;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.PlayerVelocityEvent;
import com.heirteir.autoeye.event.events.executor.EventExecutor;

public class PlayerVelocityEventExecutor extends EventExecutor<PlayerVelocityEvent> {
    public PlayerVelocityEventExecutor(Autoeye autoeye) {
        super(autoeye);
    }

    @Override public void run(PlayerVelocityEvent event) {
        if (event.getPacket().isPlayer()) {
            if (event.getPacket().getY() > 0) {
                event.getPlayer().getPhysics().setCalculatedYVelocity(event.getPacket().getY());
                event.getPlayer().getPhysics().setHasVelocity(true);
            }
        }
    }
}
