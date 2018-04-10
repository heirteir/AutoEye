package com.heirteir.autoeye.event.events;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PlayerTeleportEvent extends Event {
    public PlayerTeleportEvent(AutoEyePlayer player) {
        super(player);
    }
}
