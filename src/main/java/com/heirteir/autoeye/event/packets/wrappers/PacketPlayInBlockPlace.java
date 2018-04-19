/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.block.Block;

@Getter public class PacketPlayInBlockPlace extends PacketAbstract {
    private final Block block;

    public PacketPlayInBlockPlace(Autoeye autoeye, World world, Object packet) {
        super(packet);
        WrappedClass packetPlayInBlockPlace = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInBlockPlace");
        Object blockPosition = packetPlayInBlockPlace.getFieldByName("b").get(packet);
        WrappedClass baseBlockPosition = autoeye.getReflections().getNMSClass("BaseBlockPosition");
        Block tempBlock = null;
        try {
            tempBlock = world.getBlockAt(baseBlockPosition.getFieldByName("a").get(blockPosition), baseBlockPosition.getFieldByName("c").get(blockPosition), baseBlockPosition.getFieldByName("d").get(blockPosition));
        } catch (Exception ignored) {
        }
        this.block = tempBlock;
    }
}
