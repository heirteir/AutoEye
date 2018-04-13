package com.heirteir.autoeye.util.reflections.wrappers;

import com.heirteir.autoeye.Autoeye;
import lombok.Getter;
import org.bukkit.entity.LivingEntity;

@Getter public class WrappedEntity {
    private final Autoeye autoeye;
    private final float width, length;
    private final Object rawEntity;

    public WrappedEntity(Autoeye autoeye, LivingEntity entity) {
        this.autoeye = autoeye;
        this.rawEntity = autoeye.getReflections().getCBClass("entity.CraftEntity").getMethod("getHandle").invoke(entity);
        this.width = autoeye.getReflections().getNMSClass("Entity").getFieldByName("width").get(this.rawEntity);
        this.length = autoeye.getReflections().getNMSClass("Entity").getFieldByName("length").get(this.rawEntity);
    }
}