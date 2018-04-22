/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.api;

import com.heirteir.autoeye.player.data.InfractionData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor @Getter @Setter public class AutoEyeInfractionEvent extends org.bukkit.event.Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final InfractionData.Infraction infraction;
    private String message = "&e%player%&7 was caught using &e%check%&7. (VL=&e%vl%&7)";
    private boolean cancelled = false;

    public String getMessage() {
        return StringUtils.replace(StringUtils.replace(StringUtils.replace(this.message, "%vl%", String.valueOf(infraction.getVL() + 1)), "%check%", infraction.getParent().getName()), "%player%", player.getName());
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
