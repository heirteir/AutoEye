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

    @Override public String toString() {
        return "x: " + x + ", y: " + y + ", z: " + z;
    }
}
