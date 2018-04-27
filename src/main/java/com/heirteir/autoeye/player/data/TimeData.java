/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter public class TimeData {
    private final TimeTick lastInWater = new TimeTick(3);
    private final TimeTick lastFlying = new TimeTick(20);
    private final TimeTick lastInWeb = new TimeTick(3);
    private final TimeTick lastOnLadder = new TimeTick(3);
    private final TimeTick lastOnPiston = new TimeTick(3);
    private final TimeTick lastSolidAbove = new TimeTick(5);
    private final TimeTick lastVelocity = new TimeTick(2);
    private final TimeTick lastOnStairs = new TimeTick(4);
    private final TimeTick lastTeleport = new TimeTick(4);
    private final TimeStore connected = new TimeStore();
    private final TimeStore lastMove = new TimeStore();
    private final TimeStore lastUseEntity = new TimeStore();
    private final TimeStore secondTick = new TimeStore();
    private final TimeStore lastInKeepAlive = new TimeStore();
    private final TimeStore lastOutKeepAlive = new TimeStore();

    public long getDifference(long a, long b) {
        return b - a;
    }

    public void update(AutoEyePlayer player) {
        if (player.getLocationData().isChangedPos()) {
            this.lastInWater.decrement();
            this.lastFlying.decrement();
            this.lastInWeb.decrement();
            this.lastOnLadder.decrement();
            this.lastOnPiston.decrement();
            this.lastSolidAbove.decrement();
            this.lastVelocity.decrement();
            this.lastOnStairs.decrement();
            this.lastTeleport.decrement();
        }
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

    @RequiredArgsConstructor public static class TimeTick {
        private final int resetAmount;
        @Setter @Getter int amount;

        public void decrement() {
            this.amount--;
            this.amount = this.amount < 0 ? 0 : this.amount;
        }

        public void update() {
            this.amount = resetAmount;
        }
    }
}
