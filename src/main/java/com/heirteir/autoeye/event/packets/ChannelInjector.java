/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.packets;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.packets.channelhandler.ChannelHandler1_7;
import com.heirteir.autoeye.event.packets.channelhandler.ChannelHandler1_8;
import com.heirteir.autoeye.event.packets.channelhandler.ChannelHandlerAbstract;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter public class ChannelInjector {
    private ChannelHandlerAbstract channel;

    public void inject(Autoeye autoeye) {
        this.channel = autoeye.getReflections().classExists("io.netty.channel.Channel") ? new ChannelHandler1_8(autoeye) : new ChannelHandler1_7(autoeye);
    }

    public void addChannel(Player player) {
        this.channel.addChannel(player);
    }

    public void removeChannel(Player player) {
        this.channel.removeChannel(player);
    }
}
