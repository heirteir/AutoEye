package com.heirteir.autoeye.util.vector;

import lombok.Getter;

@Getter public class Ray3D extends Vector3D {
    private Vector3D direction;

    public Ray3D(Vector3D parent, Vector3D direction) {
        super(parent.getX(), parent.getY(), parent.getZ());
        this.direction = direction;
    }

    public Ray3D normalize() {
        return new Ray3D(new Vector3D(this.getX(), this.getY(), this.getZ()), new Vector3D(1F / direction.getX(), 1F / direction.getY(), 1F / direction.getZ()));
    }

    public Vector3D getPointAtDistance(float distance) {
        return new Vector3D(this.getX() + (direction.getX() * distance), this.getY() + (direction.getY() * distance), this.getZ() + (direction.getZ() * distance));
    }
}
