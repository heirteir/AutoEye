package com.heirteir.autoeye.util.vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter public class Vector2D {
    private final float x, y;

    public Vector3D toVector3D() {
        double x = Math.toRadians(this.x);
        double y = Math.toRadians(this.y);
        double xz = Math.cos(y);
        return new Vector3D((float) (-xz * Math.sin(x)), (float) -Math.sin(y), (float) (xz * Math.cos(x)));
    }
}
