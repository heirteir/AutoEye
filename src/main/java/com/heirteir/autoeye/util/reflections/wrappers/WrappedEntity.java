package com.heirteir.autoeye.util.reflections.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import com.heirteir.autoeye.util.vector.Vector3D;
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

    public Vector3D getVelocity() {
        WrappedClass entity = this.autoeye.getReflections().getNMSClass("Entity");
        return new Vector3D(((Double) entity.getFieldByName("motX").get(this.rawEntity)).floatValue(), ((Double) entity.getFieldByName("motY").get(this.rawEntity)).floatValue(), ((Double) entity.getFieldByName("motZ").get(this.rawEntity)).floatValue());
    }
}
