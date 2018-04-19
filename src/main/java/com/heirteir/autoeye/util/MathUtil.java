/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:46 PM
 */
package com.heirteir.autoeye.util;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

@RequiredArgsConstructor public class MathUtil {
    public float yawToLocation(AutoEyePlayer player, Entity entity) {
        return (float) java.lang.Math.atan2(entity.getLocation().getZ() - player.getLocationData().getLocation().getZ(), entity.getLocation().getX() - player.getLocationData().getLocation().getX());
    }

    public float angleTo180(float angle) {
        angle = (angle % 360F + 360F) % 360F;
        return angle > 180 ? angle - 360F : angle;
    }

    public float angleDistance(float alpha, float beta) {
        float phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }
}
