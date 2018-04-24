/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.packets.wrappers;

import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.block.Block;

@Getter public class PacketPlayInBlockPlace extends PacketAbstract {
    private static final WrappedField blockPositionField;
    private static final WrappedField blockPositionXField, blockPositionYField, blockPositionZField;
    private final Block block;

    static {
        blockPositionField = Reflections.getNMSClass("PacketPlayInBlockPlace").getFieldByName("b");
        WrappedClass baseBlockPosition = Reflections.getNMSClass("BaseBlockPosition");
        blockPositionXField = baseBlockPosition.getFieldByName("a");
        blockPositionYField = baseBlockPosition.getFieldByName("c");
        blockPositionZField = baseBlockPosition.getFieldByName("d");
    }

    public PacketPlayInBlockPlace(World world, Object packet) {
        super(packet);
        Object blockPosition = blockPositionField.get(packet);
        Block tempBlock = null;
        try {
            tempBlock = world.getBlockAt(blockPositionXField.get(blockPosition), blockPositionYField.get(blockPosition), blockPositionZField.get(blockPosition));
        } catch (Exception ignored) {
        }
        this.block = tempBlock;
    }
}
