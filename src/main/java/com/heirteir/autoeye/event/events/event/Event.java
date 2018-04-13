package com.heirteir.autoeye.event.events.event;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter public abstract class Event {
    private final AutoEyePlayer player;
}
