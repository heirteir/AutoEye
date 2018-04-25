/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.logger;

import com.heirteir.autoeye.Autoeye;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@Getter public class Logger {
    private final Autoeye autoeye;
    private final String pluginName = "&eAutoEye";
    public boolean debug = false; //TODO: Add a command to enable / disable the debug mode.

    public Logger(Autoeye autoeye) {
        this.autoeye = autoeye;
    }

    public void sendConsoleMessageWithPrefix(Object message) {
        this.sendConsoleMessage("&7[" + pluginName + "&7] &r" + message);
    }

    public void sendConsoleMessage(Object message) {
        Bukkit.getConsoleSender().sendMessage(this.translateColorCodes(message + ""));
    }

    public void sendDebugConsoleMessage(Object message) {
        if (debug) {
            Bukkit.getConsoleSender().sendMessage("[" + pluginName + " Debug] " + this.translateColorCodes(message + ""));
        }
    }


    public String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public synchronized void broadcastSyncMessage(Object message) {
        Bukkit.broadcastMessage(this.translateColorCodes(message + ""));
    }
}
