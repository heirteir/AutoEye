package com.heirteir.autoeye.event.events.executor.executors;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.PacketPlayInAbilitiesEvent;
import com.heirteir.autoeye.event.events.executor.EventExecutor;

public class PacketPlayInAbilitiesEventExecutor extends EventExecutor<PacketPlayInAbilitiesEvent> {
    public PacketPlayInAbilitiesEventExecutor(Autoeye autoeye) {
        super(autoeye);
    }

    @Override public void run(PacketPlayInAbilitiesEvent event) {
        event.getPlayer().getPhysics().setFlying(event.getPacketPlayOutAbilities().isFlying());
    }
}