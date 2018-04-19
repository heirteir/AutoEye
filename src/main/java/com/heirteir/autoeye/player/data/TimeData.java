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
