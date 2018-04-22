package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;
import org.bukkit.util.NumberConversions;

public class InvalidLocation extends Check {
    public InvalidLocation(Autoeye autoeye) {
        super(autoeye, "Invalid Location");
    }

    @EventExecutor(priority = EventExecutor.Priority.HIGHEST) public boolean check(PlayerMoveEvent event) {
        try {
            if (event.getPlayer().isConnected() && event.getPacket().isHasPos()) {
                event.getPlayer().getPlayer().getWorld().getBlockAt(NumberConversions.floor(event.getPacket().getX()), NumberConversions.floor(event.getPacket().getY()), NumberConversions.floor(event.getPacket().getZ()));
            }
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }

    @Override public <T extends Event> boolean revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
        return false;
    }
}
