package com.heirteir.autoeye.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

@RequiredArgsConstructor @Getter public class Vector {
    private final float x, y, z;

    public Vector(Entity entity) {
        this.x = (float) entity.getLocation().getX();
        this.y = (float) entity.getLocation().getY();
        this.z = (float) entity.getLocation().getZ();
    }
}
