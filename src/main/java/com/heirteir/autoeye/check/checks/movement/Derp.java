package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Derp extends Check
{
    public Derp(Autoeye autoeye)
    {
        super(autoeye, "Derp");
    }

    @EventExecutor
    public boolean check(PlayerMoveEvent event)
    {
        if (event.getPlayer().isConnected() && !event.getPlayer().getLocationData().isTeleported())
        {
            return event.getPlayer().getPlayer().getLocation().getPitch() > 90 || event.getPlayer().getPlayer().getLocation().getPitch() < -90;
        }
        return false;
    }

    @Override
    public <T extends Event> boolean revert(T event)
    {
        if (event.getPlayer().getInfractionData().getInfraction(this).getVL() > 10)
        {
            event.getPlayer().getPlayer().getPlayer().teleport(event.getPlayer().getPlayer().getLocation().setDirection(new Vector(0, 0, 0)));
            //Maybe an AutoKick? Because I'm absolutely certain there can't be any false detections.
        }
        return false;
    }
}
