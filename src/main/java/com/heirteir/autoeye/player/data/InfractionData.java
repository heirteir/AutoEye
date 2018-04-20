/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.player.data;

import com.google.common.collect.Maps;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

import java.util.Map;

public class InfractionData {
    private final Map<Class<? extends Check>, Infraction> infractions;

    public InfractionData() {
        this.infractions = Maps.newHashMap();
    }

    public Infraction getInfraction(Check check) {
        return infractions.computeIfAbsent(check.getClass(), k -> new Infraction(check));
    }

    public void addVL(AutoEyePlayer player, Check check) {
        Infraction infraction = this.getInfraction(check);
        infraction.add(player);
    }

    public static class Infraction {
        @Getter private final Check parent;
        private int threshold;
        private int amount;
        private long lastInfractionTime;
        private long lastCountTime;

        Infraction(Check parent) {
            this.parent = parent;
            this.amount = 0;
            this.lastInfractionTime = 0;
            this.threshold = 0;
            this.lastCountTime = 0;
        }

        void add(AutoEyePlayer player) {
            if (player.getTimeData().getDifference(this.lastInfractionTime, System.currentTimeMillis()) >= 20000) {
                this.amount = 0;
            }
            this.lastInfractionTime = System.currentTimeMillis();
            this.amount++;
        }

        public int getVL() {
            return this.amount;
        }

        public int addThreshold(AutoEyePlayer player, long difference) {
            if (player.getTimeData().getDifference(this.lastCountTime, System.currentTimeMillis()) >= difference) {
                this.resetThreshold();
            }
            this.lastCountTime = System.currentTimeMillis();
            return ++this.threshold;
        }

        public int addThreshold() {
            return ++this.threshold;
        }

        public void resetThreshold() {
            this.threshold = 0;
        }
    }
}
