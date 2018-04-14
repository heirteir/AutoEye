package com.heirteir.autoeye.util.vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter public class Vector2D {
    private final float x, y;

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }
}
