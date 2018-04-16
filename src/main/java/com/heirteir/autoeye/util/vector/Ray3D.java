package com.heirteir.autoeye.util.vector;

import lombok.Getter;

@Getter public class Ray3D extends Vector3D {
    Vector3D direction;

    public Ray3D(Vector3D origin, Vector3D direction) {
        super(origin.getX(), origin.getY(), origin.getZ());
        this.direction = direction.normalize();
    }

    public Vector3D getPointAtDistance(float distance) {
        return this.offset(this.direction.multiply(distance));
    }
}
