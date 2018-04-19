/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:46 PM
 */
package com.heirteir.autoeye.util.logger;

import com.heirteir.autoeye.Autoeye;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@Getter public class Logger {
    private final Autoeye autoeye;
    private final String pluginName = "&eAutoEye";

    public Logger(Autoeye autoeye) {
        this.autoeye = autoeye;
    }

    public void sendConsoleMessageWithPrefix(Object message) {
        this.sendConsoleMessage("&7[" + pluginName + "&7] &r" + message);
    }

    public void sendConsoleMessage(Object message) {
        Bukkit.getConsoleSender().sendMessage(this.translateColorCodes(message + ""));
    }

    public String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public synchronized void broadcastSyncMessage(Object message) {
        Bukkit.broadcastMessage(this.translateColorCodes(message + ""));
    }
}
