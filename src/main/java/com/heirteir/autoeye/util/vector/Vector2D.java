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

@RequiredArgsConstructor @Getter public class Vector2D {
    private final float x, y;

    public Vector2D subtract(Vector2D from) {
        return new Vector2D(this.x - from.getX(), this.y - from.getY());
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    @Override public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
