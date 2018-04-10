package com.heirteir.autoeye.event.events.executor;

import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.Event;

import java.util.Set;

public abstract class CheckEventExecutor<T extends Event> extends EventExecutor<T> {
    protected final Set<Check<T>> checks;

    public CheckEventExecutor(Autoeye autoeye) {
        super(autoeye);
        this.checks = Sets.newHashSet();
    }

    @Override public abstract void run(T event);

    protected void bulkRunChecks(T event) {
        for (Check<T> check : this.checks) {
            if (check.canRun(event) && check.check(this.autoeye, event)) {
                event.getPlayer().getInfractionData().addVL(check);
                check.revert(this.autoeye,  event);
            }
        }
    }
}
