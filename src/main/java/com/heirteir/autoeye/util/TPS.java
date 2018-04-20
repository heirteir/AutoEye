/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util;

import com.heirteir.autoeye.Autoeye;
import org.bukkit.scheduler.BukkitRunnable;

public class TPS extends BukkitRunnable {
    private final Autoeye autoeye;
    private float tps;
    private long previousTick;
    private float[] avg;
    private int index;

    public TPS(Autoeye autoeye) {
        this.autoeye = autoeye;
        this.avg = new float[20];
        this.tps = 20;
    }

    @Override public void run() {
        if (!this.autoeye.isEnabled()) {
            this.cancel();
            return;
        }
        if (this.previousTick != 0) {
            float diff = System.currentTimeMillis() - this.previousTick;
            this.avg[this.index++] = diff < 50 ? 50 : diff;
            if (this.index == this.avg.length) {
                float avg = 0;
                for (float value : this.avg) {
                    avg += value;
                }
                this.tps = 1000F / (avg / this.avg.length);
                this.index = 0;
            }
        }
        this.previousTick = System.currentTimeMillis();
    }

    public float get() {
        return tps;
    }
}
