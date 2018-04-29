package com.heirteir.autoeye.util;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedConstructor;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import com.heirteir.autoeye.util.reflections.types.WrappedMethod;
import com.heirteir.autoeye.util.server.Version;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;

import java.lang.reflect.Array;
import java.util.UUID;

public class NPC {
    private static final WrappedConstructor playerInteractManagerConstructor = Reflections.getNMSClass("PlayerInteractManager").getConstructorAtIndex(0);
    private static final WrappedConstructor gameProfileConstructor = (Reflections.classExists("com.mojang.authlib.GameProfile") ? Reflections.getClass("com.mojang.authlib.GameProfile") : Reflections.getClass("net.minecraft.util.com.mojang.authlib.GameProfile")).getConstructor(UUID.class, String.class);
    private static final WrappedConstructor packetPlayOutPlayerInfoConstructor = Reflections.getNMSClass("PacketPlayOutPlayerInfo").getConstructorAtIndex(1);
    private static final WrappedConstructor packetPlayOutNamedEntitySpawnConstructor = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getConstructorAtIndex(0);
    private static final WrappedConstructor entityPlayerConstructor = Reflections.getNMSClass("EntityPlayer").getConstructorAtIndex(0);
    private static final WrappedConstructor dataWatcherObjectConstructor = (Autoeye.getVersion().equals(Version.SEVEN) || Autoeye.getVersion().equals(Version.EIGHT)) ? null : Reflections.getNMSClass("DataWatcherObject").getConstructor(int.class, Reflections.getNMSClass("DataWatcherSerializer").getParent());
    private static final WrappedConstructor packetPlayOutEntityMetaDataConstructor = Reflections.getNMSClass("PacketPlayOutEntityMetadata").getConstructorAtIndex(1);
    private static final WrappedField enumPlayerInfoActionField = Reflections.getNMSClass("PacketPlayOutPlayerInfo").getFieldByName("a");
    private static final WrappedField idField = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("a");
    private static final WrappedField uuidField = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("b");
    private static final WrappedField xField = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("c");
    private static final WrappedField yField = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("d");
    private static final WrappedField zField = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("e");
    private static final WrappedField yawField = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("f");
    private static final WrappedField pitchField = Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("g");
    private static final WrappedField itemField = (Autoeye.getVersion().equals(Version.SEVEN) || Autoeye.getVersion().equals(Version.EIGHT)) ? Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("h") : null;
    private static final WrappedField dataWatcherField = (Autoeye.getVersion().equals(Version.SEVEN) || Autoeye.getVersion().equals(Version.EIGHT)) ? Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("i") : Reflections.getNMSClass("PacketPlayOutNamedEntitySpawn").getFieldByName("h");
    private static final WrappedField playerConnectionField = Reflections.getNMSClass("EntityPlayer").getFieldByName("playerConnection");
    private static final WrappedMethod getHandle = Reflections.getCBClass("CraftWorld").getMethod("getHandle");
    private static final WrappedMethod getServer = Reflections.getCBClass("CraftServer").getMethod("getServer");
    private static final WrappedMethod getDataWatcher = Reflections.getNMSClass("EntityPlayer").getMethod("getDataWatcher");
    private static final WrappedMethod getId = Reflections.getNMSClass("EntityPlayer").getMethod("getId");
    private static final WrappedMethod setMethod = (Autoeye.getVersion().equals(Version.SEVEN) || Autoeye.getVersion().equals(Version.EIGHT)) ? Reflections.getNMSClass("DataWatcher").getMethod("watch", int.class, Object.class) : Reflections.getNMSClass("DataWatcher").getMethod("set", Reflections.getNMSClass("DataWatcherObject").getParent(), Object.class);
    private static final Enum ADD_PLAYER = Reflections.getClass(enumPlayerInfoActionField.getField().getType()).getEnum("ADD_PLAYER");
    private static final Enum REMOVE_PLAYER = Reflections.getClass(enumPlayerInfoActionField.getField().getType()).getEnum("REMOVE_PLAYER");
    private static final Object byteObject = (Autoeye.getVersion().equals(Version.SEVEN) || Autoeye.getVersion().equals(Version.EIGHT)) ? null : Reflections.getNMSClass("DataWatcherRegistry").getFieldByName("a").get(null);
    private final AutoEyePlayer player;
    private final UUID uuid = UUID.randomUUID();
    private final EntityPlayer entity;
    private int id;

    public NPC(AutoEyePlayer player) {
        this.player = player;
        this.entity = entityPlayerConstructor.newInstance(getServer.invoke(Bukkit.getServer()), getHandle.invoke(player.getPlayer().getWorld()), gameProfileConstructor.newInstance(uuid, uuid.toString().substring(0, 8)), playerInteractManagerConstructor.newInstance((Object) getHandle.invoke(player.getPlayer().getWorld())));
        this.id = getId.invoke(this.entity);
        playerConnectionField.set(this.entity, null);
        this.spawn();
        this.setInvisible();
    }

    private int specialFloor(double pos) {
        int posInteger = (int) (pos * 32D);
        return pos < (double) posInteger ? posInteger - 1 : posInteger;
    }

    public void setInvisible() {
        Object dataWatcher = getDataWatcher.invoke(this.entity);
        if (Autoeye.getVersion().equals(Version.SEVEN) || Autoeye.getVersion().equals(Version.EIGHT)) {
            setMethod.invoke(dataWatcher, 0, (byte) 0x20);
        } else {
            setMethod.invoke(dataWatcher, dataWatcherObjectConstructor.newInstance(), byteObject, (byte) 0x20);
        }
        this.player.getWrappedEntity().sendPacket(packetPlayOutEntityMetaDataConstructor.newInstance(this.id, dataWatcher, false));
    }

    public void spawn() {
        Object array = Array.newInstance(this.entity.getClass(), 1);
        Array.set(array, 0, this.entity);
        player.getWrappedEntity().sendPacket(packetPlayOutPlayerInfoConstructor.newInstance(ADD_PLAYER, array));
        Object packetPlayOutNamedEntitySpawn = packetPlayOutNamedEntitySpawnConstructor.newInstance();
        idField.set(packetPlayOutNamedEntitySpawn, id);
        uuidField.set(packetPlayOutNamedEntitySpawn, uuid);
        yawField.set(packetPlayOutNamedEntitySpawn, (byte) 0);
        pitchField.set(packetPlayOutNamedEntitySpawn, (byte) 0);
        Object x, y, z;
        if (Autoeye.getVersion().equals(Version.SEVEN) || Autoeye.getVersion().equals(Version.EIGHT)) {
            x = specialFloor(player.getLocationData().getLocation().getX());
            y = specialFloor(player.getLocationData().getLocation().getY());
            z = specialFloor(player.getLocationData().getLocation().getZ());
            itemField.set(packetPlayOutNamedEntitySpawn, 0);
        } else {
            x = (double) player.getLocationData().getLocation().getX();
            y = (double) player.getLocationData().getLocation().getY();
            z = (double) player.getLocationData().getLocation().getZ();
        }
        dataWatcherField.set(packetPlayOutNamedEntitySpawn, getDataWatcher.invoke(this.entity));
        xField.set(packetPlayOutNamedEntitySpawn, x);
        yField.set(packetPlayOutNamedEntitySpawn, y);
        zField.set(packetPlayOutNamedEntitySpawn, z);
        player.getWrappedEntity().sendPacket(packetPlayOutNamedEntitySpawn);
        player.getWrappedEntity().sendPacket(packetPlayOutPlayerInfoConstructor.newInstance(REMOVE_PLAYER, array));
    }
}
