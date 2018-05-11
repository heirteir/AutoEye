/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util;

import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.vector.Vector3D;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

@RequiredArgsConstructor public class MathUtil {
    public Vector3D getVectorToLocation(AutoEyePlayer player, Entity entity) {
        return this.getVectorToLocation(player, new Vector3D((float) entity.getLocation().getX(), (float) entity.getLocation().getY(), (float) entity.getLocation().getZ()));
    }

    public Vector3D getVectorToLocation(AutoEyePlayer player, Vector3D location) {
        return location.subtract(player.getLocationData().getLocation().offset(0, (float) player.getPlayer().getEyeHeight(), 0));
    }

    public float getAngleTo180(float angle) {
        angle = (angle % 360F + 360F) % 360F;
        return angle > 180 ? angle - 360F : angle;
    }

    public float getAngleDistance(float alpha, float beta) {
        float phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }
}
