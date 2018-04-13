package com.heirteir.autoeye.util;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

@RequiredArgsConstructor public class MathUtil {
    public float yawToLocation(AutoEyePlayer player, Entity entity) {
        return (float) java.lang.Math.atan2(entity.getLocation().getZ() - player.getLocationData().getLocation().getZ(), entity.getLocation().getX() - player.getLocationData().getLocation().getX());
    }

    public float radiansToDegrees(float radian) {
        return radian * 57.2958F;
    }

    public float angleTo180(float angle) {
        angle = (angle % 360F + 360F) % 360F;
        return angle > 180 ? angle - 360F : angle;
    }

    public float distance(float alpha, float beta) {
        float output = (alpha - beta + 180F) % 360F - 180F;
        output += output < -180F ? 360F : (output > 180) ? -360 : 0;
        return output;
    }
}
