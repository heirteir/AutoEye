/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 8:11 PM
 */
package com.heirteir.autoeye.player;

import com.google.common.collect.Maps;
import com.heirteir.autoeye.Autoeye;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

@Getter public class AutoEyePlayerList implements Listener {
    private final Autoeye autoeye;
    private final Map<UUID, AutoEyePlayer> players;

    public AutoEyePlayerList(Autoeye autoeye) {
        players = Maps.newHashMap();
        this.autoeye = autoeye;
    }

    public void createListener() {
        Bukkit.getPluginManager().registerEvents(this, this.autoeye);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    public AutoEyePlayer getPlayer(Player player) {
        return this.players.computeIfAbsent(player.getUniqueId(), k -> new AutoEyePlayer(autoeye, player));
    }

    @EventHandler public void onPlayerJoin(PlayerJoinEvent e) {
        this.autoeye.getChannelInjector().addChannel(e.getPlayer());
    }

    @EventHandler public void onPlayerQuit(PlayerQuitEvent e) {
        this.players.remove(e.getPlayer().getUniqueId());
        this.autoeye.getChannelInjector().removeChannel(e.getPlayer());
    }

    @EventHandler public void onPlayerKick(PlayerKickEvent e) {
        this.players.remove(e.getPlayer().getUniqueId());
        this.autoeye.getChannelInjector().removeChannel(e.getPlayer());
    }
}
