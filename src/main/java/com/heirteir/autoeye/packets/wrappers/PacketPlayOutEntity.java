package com.heirteir.autoeye.packets.wrappers;

import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import lombok.Getter;

@Getter public class PacketPlayOutEntity extends PacketAbstract {
    private static final WrappedField idField = Reflections.getNMSClass("PacketPlayOutEntity").getFieldByName("a");
    private final int id;

    public PacketPlayOutEntity(Object packet) {
        super(packet);
        this.id = idField.get(packet);
    }
}
