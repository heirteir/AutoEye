/*
 * Created by Justin Heflin on 4/20/18 12:38 AM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 10:55 PM
 */
package com.heirteir.autoeye.check;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.permissions.Permission;

public abstract class Check {
    protected final Autoeye autoeye;
    @Getter private final CheckType type;
    @Getter private final Permission permission;
    @Getter private final String name;
    @Getter @Setter protected boolean enabled;
    @Getter @Setter protected boolean revert;

    public Check(Autoeye autoeye, CheckType type, String name) {
        this.autoeye = autoeye;
        this.type = type;
        this.name = name;
        this.permission = this.autoeye.getPermissionsManager().addParent(this.autoeye.getPermissionsManager().getBypass(), this.autoeye.getPermissionsManager().createPermission("autoeye.bypass." + StringUtils.replace(StringUtils.replace(StringUtils.replace(this.name, " ", "_"), "(", ""), ")", "").toLowerCase()));
        this.enabled = true;
    }

    public abstract boolean check(AutoEyePlayer player);

    public abstract boolean revert(AutoEyePlayer player);

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
