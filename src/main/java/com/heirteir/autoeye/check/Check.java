package com.heirteir.autoeye.check;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public abstract class Check<T extends Event> {
    private final String name;

    public Check(String name) {
        this.name = name;
    }

    public abstract boolean check(Autoeye autoeye, T event);

    public abstract boolean canRun(T event);

    public abstract void revert(Autoeye autoeye, T event);

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
