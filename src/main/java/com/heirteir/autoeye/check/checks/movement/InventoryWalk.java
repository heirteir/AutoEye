package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryWalk extends Check {
    public InventoryWalk(Autoeye autoeye) {
        super(autoeye, "Inventory Walk");
    }

    @EventExecutor
    public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().getPlayer().getOpenInventory().getType() != InventoryType.CRAFTING && !event.getPlayer().getLocationData().isTeleported() && event.getPlayer().isConnected() && event.getPacket().isHasPos() && !event.getPlayer().getPhysics().isFlying() && event.getPlayer().getPhysics().getCalculatedYVelocity() >= 0 && event.getPlayer().getLocationData().isChangedPos() && (!event.getPlayer().getLocationData().isOnIce() && !event.getPlayer().getLocationData().isOnLadder() && !event.getPlayer().getLocationData().isOnPiston() && !event.getPlayer().getLocationData().isOnSlime() && !event.getPlayer().getLocationData().isInWater()) && this.checkThreshold(event.getPlayer(), 8, 100L)) {
            return !event.getPlayer().getPhysics().isHasVelocity();
        }
        return false;
    }

    @Override
    public <T extends Event> boolean revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
        return false;
    }
}
