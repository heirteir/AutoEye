package com.heirteir.autoeye.event.packets;

import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.packets.channelhandler.ChannelHandler1_7;
import com.heirteir.autoeye.event.packets.channelhandler.ChannelHandler1_8;
import com.heirteir.autoeye.event.packets.channelhandler.ChannelHandlerAbstract;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter public class ChannelInjector {
    private ChannelHandlerAbstract channel;

    public void inject(Autoeye autoeye) {
        this.channel = autoeye.getReflections().classExists("io.netty.channel.Channel") ? new ChannelHandler1_8(autoeye) : new ChannelHandler1_7(autoeye);
        for (Player player : Sets.newHashSet(Bukkit.getOnlinePlayers())) {
            this.addChannel(player);
        }
    }

    public void addChannel(Player player) {
        this.channel.addChannel(player);
    }

    public void removeChannel(Player player) {
        this.channel.removeChannel(player);
    }
}
