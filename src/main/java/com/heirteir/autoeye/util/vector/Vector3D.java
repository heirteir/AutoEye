/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
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
