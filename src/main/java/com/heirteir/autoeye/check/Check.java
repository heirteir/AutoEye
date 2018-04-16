package com.heirteir.autoeye.check;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.Listener;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

public abstract class Check extends Listener {
    protected final Autoeye autoeye;
    @Getter private final String name;

    public Check(Autoeye autoeye, String name) {
        super(EventExecutor.Priority.NORMAL);
        this.autoeye = autoeye;
        this.name = name;
    }

    public abstract <T extends Event> void revert(T event);

    protected boolean checkThreshold(AutoEyePlayer player, int amount) {
        return player.getInfractionData().getInfraction(this.getClass()).addThreshold() >= amount;
    }

    protected boolean checkThreshold(AutoEyePlayer player, int amount, long time) {
        return player.getInfractionData().getInfraction(this.getClass()).addThreshold(player, time) >= amount;
    }

    protected boolean resetThreshold(AutoEyePlayer player) {
        player.getInfractionData().getInfraction(this.getClass()).resetThreshold();
        return false;
    }
}
