package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PacketPlayInKeepAlive extends PacketAbstract {

    private long time;

    public PacketPlayInKeepAlive(AutoEyePlayer player, Object packet) {
        super(packet);

        time = System.currentTimeMillis();
    }
}
