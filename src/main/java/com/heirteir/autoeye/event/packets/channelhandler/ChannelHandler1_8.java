package com.heirteir.autoeye.event.packets.channelhandler;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.player.AutoEyePlayer;
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
        return (io.netty.channel.Channel) this.autoeye.getReflections().getNMSClass("NetworkManager").getFirstFieldByType(io.netty.channel.Channel.class).get(networkManagerField.get(playerConnectionField.get(autoeye.getReflections().getEntityPlayer(player))));
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
            channelHandlerAbstract.packetSent(this.player, msg);
        }

        @Override public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            channelHandlerAbstract.packetReceived(this.player, msg);
        }
    }
}
