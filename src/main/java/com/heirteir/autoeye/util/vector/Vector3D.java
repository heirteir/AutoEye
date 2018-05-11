/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter public class Vector3D {
    private final float x, y, z;

    public Vector3D offset(float x, float y, float z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }

    public float distance(Vector3D to) {
        return (float) Math.sqrt(Math.pow(to.getX() - this.x, 2) + Math.pow(to.getY() - this.y, 2) + Math.pow(to.getZ() - this.z, 2));
    }

    public Vector3D add(Vector3D vector3D) {
        return this.offset(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public Vector3D subtract(Vector3D vector3D) {
        return this.offset(-vector3D.getX(), -vector3D.getY(), -vector3D.getZ());
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.y * this.y);
    }

    public Vector3D midpoint(Vector3D other) {
        return new Vector3D((this.x + other.getX()) / 2F, (this.y + other.getY()) / 2F, (this.z + other.getZ()) / 2F);
    }

    public float dot(Vector3D vector3D) {
        return this.x * vector3D.getX() + this.y * vector3D.getY() + this.z * vector3D.getZ();
    }

    public float angle(Vector3D vector3D) {
        return (float) Math.acos(this.dot(vector3D) / (this.length() * vector3D.length()));
    }

    public Vector3D normalize() {
        float length = this.length();
        return length > 0 ? new Vector3D(this.x / length, this.y / length, this.z / length) : this;
    }

    @Override public String toString() {
        return "x: " + x + ", y: " + y + ", z: " + z;
    }
}
