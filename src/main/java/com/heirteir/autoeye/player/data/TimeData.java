/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.player.data;

import lombok.Getter;

@Getter public class TimeData {
    private final TimeStore lastTeleport = new TimeStore();
    private final TimeStore lastInWater = new TimeStore();
    private final TimeStore lastFlying = new TimeStore();
    private final TimeStore lastInWeb = new TimeStore();
    private final TimeStore lastOnLadder = new TimeStore();
    private final TimeStore lastOnPiston = new TimeStore();
    private final TimeStore lastUseEntity = new TimeStore();
    private final TimeStore lastSolidAbove = new TimeStore();
    private final TimeStore secondTick = new TimeStore();
    private final TimeStore connected = new TimeStore();
    private final TimeStore lastInKeepAlive = new TimeStore();
    private final TimeStore lastOutKeepAlive = new TimeStore();
    private final TimeStore lastVelocity = new TimeStore();

    public long getDifference(long a, long b) {
        return b - a;
    }

    public static class TimeStore {
        @Getter long time;

        public void update() {
            this.time = System.currentTimeMillis();
        }

        public long getDifference() {
            return System.currentTimeMillis() - this.time;
        }
    }
}
