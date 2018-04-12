package com.heirteir.autoeye.util.reflections;

import com.google.common.collect.Maps;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import com.heirteir.autoeye.util.reflections.types.WrappedMethod;
import lombok.Getter;
import org.bukkit.entity.Player;

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

    public WrappedClass getClass(String name) {
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
