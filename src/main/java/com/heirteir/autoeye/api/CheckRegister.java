package com.heirteir.autoeye.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.check.checks.combat.Reach;
import com.heirteir.autoeye.check.checks.movement.*;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor public class CheckRegister {
    private final Autoeye autoeye;
    private final Map<CheckType, Map<String, Check>> checks = Maps.newHashMap();

    public void addCheck(Check check) {
        this.getCheckMap(check.getType()).put(check.getName(), check);
    }

    public Map<String, Check> getCheckMap(CheckType type) {
        return this.checks.computeIfAbsent(type, k -> Maps.newHashMap());
    }

    public Collection<Check> getCheckList(CheckType type) {
        return this.getCheckMap(type).values();
    }

    public Collection<Check> getAllChecks() {
        List<Check> checks = Lists.newArrayList();
        this.checks.values().forEach(map -> checks.addAll(map.values()));
        checks.sort(Comparator.comparingInt(check -> check.getType().getOrder()));
        return checks;
    }

    public void registerDefaultChecks() {
        this.addCheck(new Reach(this.autoeye));
        this.addCheck(new InvalidLocation(this.autoeye));
        this.addCheck(new FastLadder(this.autoeye));
        this.addCheck(new InvalidMotion(this.autoeye));
        this.addCheck(new NoWeb(this.autoeye));
        this.addCheck(new Speed(this.autoeye));
        this.addCheck(new SpoofedOnGroundPacket(this.autoeye));
        this.addCheck(new Step(this.autoeye));
        this.addCheck(new Timer(this.autoeye));
        this.addCheck(new SlimeJump(this.autoeye));
        this.addCheck(new NoFall(this.autoeye));
        this.addCheck(new InvalidPitch(this.autoeye));
        this.addCheck(new InventoryWalk(this.autoeye));
    }

    public void unregisterChecks() {
        this.checks.clear();
    }
}
