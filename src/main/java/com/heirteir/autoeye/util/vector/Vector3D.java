package com.heirteir.autoeye.util.vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter public class Vector3D {
    private final float x, y, z;

    public Vector3D offset(float x, float y, float z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }

    @Override public String toString() {
        return "x: " + x + ", y: " + y + ", z: " + z;
    }
}
