/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.check;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.Listener;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.permissions.Permission;

public abstract class Check extends Listener {
    protected final Autoeye autoeye;
    @Getter private final Permission permission;
    @Getter private final String name;

    public Check(Autoeye autoeye, String name) {
        this.autoeye = autoeye;
        this.name = name;
        this.permission = this.autoeye.getPermissionsManager().addParent(this.autoeye.getPermissionsManager().getBypass(), this.autoeye.getPermissionsManager().createPermission("autoeye.bypass." + StringUtils.replace(StringUtils.replace(StringUtils.replace(this.name, " ", "_"), "(", ""), ")", "").toLowerCase()));
    }

    public abstract <T extends Event> void revert(T event);

    protected boolean checkThreshold(AutoEyePlayer player, int amount) {
        return player.getInfractionData().getInfraction(this).addThreshold() >= amount;
    }

    protected boolean checkThreshold(AutoEyePlayer player, int amount, long time) {
        return player.getInfractionData().getInfraction(this).addThreshold(player, time) >= amount;
    }

    protected boolean resetThreshold(AutoEyePlayer player) {
        player.getInfractionData().getInfraction(this).resetThreshold();
        return false;
    }
}
