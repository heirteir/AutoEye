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

    private String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public synchronized void broadcastSyncMessage(Object message) {
        Bukkit.broadcastMessage(this.translateColorCodes(message + ""));
    }
}
