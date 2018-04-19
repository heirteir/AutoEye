/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
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
            if (channel != null && channel.pipeline().get(this.playerKey) == null) {
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
            super.write(ctx, msg, promise);
            channelHandlerAbstract.run(this.player, msg);
        }

        @Override public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            channelHandlerAbstract.run(this.player, msg);
        }
    }
}
