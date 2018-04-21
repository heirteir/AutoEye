/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.packets.channelhandler;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedEntity;
import org.bukkit.entity.Player;

public class ChannelHandler1_8 extends ChannelHandlerAbstract {
    public ChannelHandler1_8(Autoeye autoeye) {
        super(autoeye);
    }

    @Override public void addChannel(Player player) {
        io.netty.channel.Channel channel = getChannel(player);
        this.addChannelHandlerExecutor.execute(() -> {
            if (channel != null) {
                if (channel.pipeline().get(this.playerKey) != null) {
                    channel.pipeline().remove(this.playerKey);
                }
                channel.pipeline().addBefore(this.handlerKey, this.playerKey, new ChannelHandler(autoeye, player, this));
            }
        });
    }

    @Override public void removeChannel(Player player) {
        io.netty.channel.Channel channel = getChannel(player);
        this.removeChannelHandlerExecutor.execute(() -> {
            if (channel != null && channel.pipeline().get(this.playerKey) != null) {
                channel.pipeline().remove(this.playerKey);
            }
        });
    }

    private io.netty.channel.Channel getChannel(Player player) {
        return (io.netty.channel.Channel) this.autoeye.getReflections().getNMSClass("NetworkManager").getFirstFieldByType(io.netty.channel.Channel.class).get(networkManagerField.get(playerConnectionField.get(new WrappedEntity(autoeye, player).getRawEntity())));
    }

    private static class ChannelHandler extends io.netty.channel.ChannelDuplexHandler {
        private final AutoEyePlayer player;
        private final ChannelHandlerAbstract channelHandlerAbstract;

        ChannelHandler(Autoeye autoeye, Player player, ChannelHandlerAbstract channelHandlerAbstract) {
            this.player = autoeye.getAutoEyePlayerList().getPlayer(player);
            this.channelHandlerAbstract = channelHandlerAbstract;
        }

        @Override public void write(io.netty.channel.ChannelHandlerContext ctx, Object msg, io.netty.channel.ChannelPromise promise) throws Exception {
            if (channelHandlerAbstract.run(this.player, msg)) {
                super.write(ctx, msg, promise);
            }
        }

        @Override public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
            if (channelHandlerAbstract.run(this.player, msg)) {
                super.channelRead(ctx, msg);
            }
        }
    }
}
