/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.reflections.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import com.heirteir.autoeye.util.reflections.types.WrappedMethod;
import com.heirteir.autoeye.util.server.Version;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

@Getter public class WrappedEntity {
    private static final boolean elytraEnabled = !Autoeye.getVersion().equals(Version.SEVEN) && !Autoeye.getVersion().equals(Version.EIGHT);
    private static final WrappedMethod getHandleMethod = Reflections.getCBClass("entity.CraftEntity").getMethod("getHandle");
    private static final WrappedMethod getFlag = Reflections.getNMSClass("Entity").getMethod("getFlag", int.class);
    private static final WrappedField widthField = Reflections.getNMSClass("Entity").getFieldByName("width");
    private static final WrappedField lengthField = Reflections.getNMSClass("Entity").getFieldByName("length");
    private static final WrappedField playerConnectionField = Reflections.getNMSClass("EntityPlayer").getFieldByName("playerConnection");
    private static final WrappedMethod sendPacketMethod = Reflections.getNMSClass("PlayerConnection").getMethod("sendPacket", Reflections.getNMSClass("Packet").getParent());
    private final Autoeye autoeye;
    private final float width, length;
    private final Object rawEntity;
    private final Entity bukkitEntity;

    public WrappedEntity(Autoeye autoeye, LivingEntity entity) {
        this.autoeye = autoeye;
        this.bukkitEntity = entity;
        this.rawEntity = getHandleMethod.invoke(entity);
        this.width = widthField.get(this.rawEntity);
        this.length = lengthField.get(this.rawEntity);
    }

    public void sendPacket(Object packet) {
        sendPacketMethod.invoke(playerConnectionField.get(this.rawEntity), packet);
    }

    public boolean isGliding() {
        return elytraEnabled && (boolean) getFlag.invoke(rawEntity, 7);
    }
}
