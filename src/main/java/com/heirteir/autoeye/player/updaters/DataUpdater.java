package com.heirteir.autoeye.player.updaters;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.*;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.Listener;
import com.heirteir.autoeye.util.vector.Vector3D;

public class DataUpdater extends Listener {
    private final Autoeye autoeye;

    public DataUpdater(Autoeye autoeye) {
        super(EventExecutor.Priority.HIGH);
        this.autoeye = autoeye;
    }

    @EventExecutor(event = PlayerMoveEvent.class) public void run(PlayerMoveEvent event) {
        event.getPlayer().update(this.autoeye, event);
    }

    @EventExecutor(event = PacketPlayInAbilitiesEvent.class) public void run(PacketPlayInAbilitiesEvent event) {
        event.getPlayer().getPhysics().setFlying(event.getPacket().isFlying());
    }

    @EventExecutor(event = PacketPlayInUseEntityEvent.class) public void run(PacketPlayInUseEntityEvent event) {
        event.getPlayer().getAttackData().setLastEntity(event.getPacket().getEntity());
        event.getPlayer().getAttackData().setLastActionType(event.getPacket().getActionType());
        event.getPlayer().getTimeData().getLastUseEntity().update();
    }

    @EventExecutor(event = PlayerVelocityEvent.class) public void run(PlayerVelocityEvent event) {
        event.getPlayer().getPhysics().setServerVelocity(new Vector3D(event.getPacket().getX(), event.getPacket().getY(), event.getPacket().getZ()));
        if (event.getPacket().getY() > 0 || event.getPacket().getY() < -0.07839966F) {
            event.getPlayer().getPhysics().setCalculatedYVelocity(event.getPacket().getY());
            event.getPlayer().getPhysics().setHasVelocity(true);
        } else {
            event.getPlayer().getPhysics().setCalculatedYVelocity(0);
            event.getPlayer().getPhysics().setHasVelocity(true);
        }
    }

    @EventExecutor(event = PlayerTeleportEvent.class) public void run(PlayerTeleportEvent event) {
        event.getPlayer().getLocationData().reset(this.autoeye, event.getPlayer());
        event.getPlayer().getPhysics().reset(event.getPlayer());
        event.getPlayer().getTimeData().getLastTeleport().update();
        event.getPlayer().getLocationData().setTeleported(true);
    }

    @EventExecutor(event = PacketPlayInKeepAliveEvent.class)
    public void run(PacketPlayInKeepAliveEvent event) {
        event.getPlayer().getTimeData().getLastKeepAlive().update();
    }
}
