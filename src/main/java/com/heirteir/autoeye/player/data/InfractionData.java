package com.heirteir.autoeye.player.data;

import com.google.common.collect.Maps;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.util.TimeUtil;

import java.util.Map;

public class InfractionData {
    private final Autoeye autoeye;
    private final Map<Class<? extends Check>, Infraction> infractions;

    public InfractionData(Autoeye autoeye) {
        this.autoeye = autoeye;
        this.infractions = Maps.newHashMap();
    }

    public Infraction getInfraction(Class<? extends Check> check) {
        return infractions.computeIfAbsent(check, k -> new Infraction());
    }

    public void addVL(Check check) {
        Infraction infraction = this.getInfraction(check.getClass());
        infraction.add();
        this.autoeye.getPluginLogger().broadcastSyncMessage(check.getName() + " " + infraction.getAmount());
    }

    public static class Infraction {
        private int threshold;
        private int amount;
        private long lastInfractionTime;
        private long lastCountTime;

        Infraction() {
            this.amount = 0;
            this.lastInfractionTime = 0;
            this.threshold = 0;
            this.lastCountTime = 0;
        }

        void add() {
            if (TimeUtil.getTimeDifference(System.currentTimeMillis(), this.lastInfractionTime) >= 20000) {
                this.amount = 0;
            }
            this.lastInfractionTime = System.currentTimeMillis();
            this.amount++;
        }

        public int getAmount() {
            return this.amount;
        }

        public int addThreshold(long difference) {
            if (TimeUtil.getTimeDifference(System.currentTimeMillis(), this.lastCountTime) >= difference) {
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
