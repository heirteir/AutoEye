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
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Getter public class ChannelInjector {
    private ChannelHandlerAbstract channel;

    public void inject(Autoeye autoeye) {
        this.channel = autoeye.getReflections().classExists("io.netty.channel.Channel") ? new ChannelHandler1_8(autoeye) : new ChannelHandler1_7(autoeye);
        //alternative to Bukkit.getOnlinePlayers() for compatibility
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Player) {
                    this.removeChannel((Player) entity);
                    this.addChannel((Player) entity);
                }
            }
        }
    }

    public void addChannel(Player player) {
        this.channel.addChannel(player);
    }

    public void removeChannel(Player player) {
        this.channel.removeChannel(player);
    }
}
