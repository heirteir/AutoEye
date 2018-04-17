package com.heirteir.autoeye.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor @Getter public class MethodListenerPair {
    private final Method method;
    private final Listener listener;
}
