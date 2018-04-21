/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.player.updaters;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.Listener;
import com.heirteir.autoeye.event.events.event.*;
import com.heirteir.autoeye.util.vector.Vector3D;

public class DataUpdater extends Listener {
    private final Autoeye autoeye;

    public DataUpdater(Autoeye autoeye) {
        this.autoeye = autoeye;
    }

    @EventExecutor(priority = EventExecutor.Priority.HIGH) public void run(PlayerMoveEvent event) {
        event.getPlayer().update(this.autoeye, event);
    }

    @EventExecutor(priority = EventExecutor.Priority.LOWEST) public void postRun(PlayerMoveEvent event) {
        if (event.getPlayer().getPhysics().isStartVelocity() == event.getPlayer().getPhysics().isHasVelocity()) {
            event.getPlayer().getPhysics().setHasVelocity(false);
        }
    }

    @EventExecutor(priority = EventExecutor.Priority.HIGH) public void run(PacketPlayInAbilitiesEvent event) {
        event.getPlayer().getPhysics().setFlying(event.getPacket().isFlying());
    }

    @EventExecutor(priority = EventExecutor.Priority.HIGH) public void run(PacketPlayInUseEntityEvent event) {
        event.getPlayer().getAttackData().setLastEntity(event.getPacket().getEntity());
        event.getPlayer().getAttackData().setLastActionType(event.getPacket().getActionType());
        event.getPlayer().getTimeData().getLastUseEntity().update();
    }

    @EventExecutor(priority = EventExecutor.Priority.HIGH) public void run(PlayerVelocityEvent event) {
        event.getPlayer().getPhysics().setServerVelocity(new Vector3D(event.getPacket().getX(), event.getPacket().getY(), event.getPacket().getZ()));
        if (Math.abs(event.getPacket().getY()) > 0 || event.getPacket().getY() > 0) {
            event.getPlayer().getPhysics().setHasVelocity(true);
            event.getPlayer().getTimeData().getLastVelocity().update();
        }
    }

    @EventExecutor(priority = EventExecutor.Priority.HIGH) public void run(PlayerTeleportEvent event) {
        event.getPlayer().getLocationData().reset(this.autoeye, event.getPlayer());
        event.getPlayer().getPhysics().reset(event.getPlayer());
        event.getPlayer().getTimeData().getLastTeleport().update();
        event.getPlayer().getLocationData().setTeleported(true);
    }
}
