/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.reflections;

import com.google.common.collect.Maps;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

import java.util.Map;

@Getter public class Reflections {
    private final Map<String, WrappedClass> classes;
    private final String craftBukkitString;
    private final String netMinecraftServerString;
    private final PacketData packetData;

    public Reflections(String bukkitVersion) {
        String version = bukkitVersion.split("\\.")[3];
        this.classes = Maps.newHashMap();
        this.craftBukkitString = "org.bukkit.craftbukkit." + version + ".";
        this.netMinecraftServerString = "net.minecraft.server." + version + ".";
        this.packetData = new PacketData();
    }

    public boolean classExists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public WrappedClass getCBClass(String name) {
        return this.getClass(craftBukkitString + name);
    }

    public WrappedClass getNMSClass(String name) {
        return this.getClass(netMinecraftServerString + name);
    }

    private WrappedClass getClass(String name) {
        return this.classes.computeIfAbsent(name, k -> {
            try {
                return new WrappedClass(Class.forName(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public class PacketData {
        private final Map<String, WrappedClass> data;

        PacketData() {
            this.data = Maps.newHashMap();
        }

        public WrappedClass getWrappedPacketClass(String name) {
            return this.data.computeIfAbsent(name, k -> Reflections.this.getClass(name));
        }
    }
}
