/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.events;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PlayerTeleportEvent extends Event {
    public PlayerTeleportEvent(AutoEyePlayer player) {
        super(player);
    }
}
