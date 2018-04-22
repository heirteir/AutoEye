/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import lombok.Getter;

@Getter public class PacketPlayInAbilities extends PacketAbstract {
    private static final WrappedField flyingField = Reflections.getNMSClass("PacketPlayInAbilities").getFieldByName("b");
    private final boolean flying;

    public PacketPlayInAbilities(Object packet) {
        super(packet);
        this.flying = flyingField.get(packet);
    }
}
