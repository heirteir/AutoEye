/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:54 PM
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

@RequiredArgsConstructor @Getter public class AutoEyeInfractionEvent extends org.bukkit.event.Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final InfractionData.Infraction infraction;
    @Setter private String message = "&e%player%&7 was caught using &e%check%&7. (VL=&e%vl%&7)";
    private boolean cancelled = false;

    public String getMessage() {
        return StringUtils.replace(StringUtils.replace(StringUtils.replace(this.message, "%vl%", String.valueOf(infraction.getVL() + 1)), "%check%", infraction.getParent().getName()), "%player%", player.getName());
    }

    @Override public boolean isCancelled() {
        return this.cancelled;
    }

    @Override public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override public HandlerList getHandlers() {
        return handlers;
    }
}
