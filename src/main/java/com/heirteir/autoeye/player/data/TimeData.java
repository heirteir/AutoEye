package com.heirteir.autoeye.player.data;

import lombok.Getter;

@Getter public class TimeData {
    private final TimeStore lastTeleport = new TimeStore();
    private final TimeStore lastInWater = new TimeStore();
    private final TimeStore lastFlying = new TimeStore();
    private final TimeStore lastInWeb = new TimeStore();
    private final TimeStore lastOnLadder = new TimeStore();
    private final TimeStore lastOnGround = new TimeStore();

    public static class TimeStore {
        long time;

        public void update() {
            this.time = System.currentTimeMillis();
        }

        public long getDifference() {
            return System.currentTimeMillis() - this.time;
        }
    }
}
