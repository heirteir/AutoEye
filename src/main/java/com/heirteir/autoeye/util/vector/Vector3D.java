package com.heirteir.autoeye.util.vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.util.Vector;

@RequiredArgsConstructor @Getter public class Vector3D {
    private final float x, y, z;

    public Vector3D(Vector vector) {
        this((float) vector.getX(), (float) vector.getY(), (float) vector.getZ());
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D offset(float x, float y, float z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }

    public Vector3D offset(Vector3D vector3D) {
        return this.offset(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public Vector3D subtract(Vector3D vector3D) {
        return new Vector3D(this.x - vector3D.x, this.y - vector3D.y, this.z - vector3D.z);
    }

    public Vector3D absoluteValue() {
        return new Vector3D(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    public Vector3D multiply(float amount) {
        return new Vector3D(this.x * amount, this.y * amount, this.z * amount);
    }

    @Override public String toString() {
        return "x: " + x + ", y: " + y + ", z: " + z;
    }
}
