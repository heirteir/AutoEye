package com.heirteir.autoeye.util.reflections.wrappers;

import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;

@Getter public class WrappedAxisAlignedBB {
    private final Autoeye autoeye;
    private final World bukkitWorld;
    private final Object axisAlignedBB;
    private final Object world;

    private WrappedAxisAlignedBB(Autoeye autoeye, World world, Object axisAlignedBB) {
        this.autoeye = autoeye;
        this.axisAlignedBB = axisAlignedBB;
        this.bukkitWorld = world;
        this.world = autoeye.getReflections().getCBClass("CraftWorld").getMethod("getHandle").invoke(world);
    }

    public WrappedAxisAlignedBB(Autoeye autoeye, Player player) {
        this(autoeye, player.getWorld(), autoeye.getReflections().getNMSClass("EntityPlayer").getMethod("getBoundingBox").invoke(autoeye.getReflections().getEntityPlayer(player)));
    }

    public WrappedAxisAlignedBB offset(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new WrappedAxisAlignedBB(this.autoeye, this.bukkitWorld, autoeye.getReflections().getNMSClass("AxisAlignedBB").getConstructor(double.class, double.class, double.class, double.class, double.class, double.class).newInstance(this.get("a") + minX, this.get("b") + minY, this.get("c") + minZ, this.get("d") + maxX, this.get("e") + maxY, this.get("f") + maxZ));
    }

    public double get(String value) {
        return this.get(this.axisAlignedBB, value);
    }

    private double get(Object axisAlignedBB, String value) {
        return (double) this.autoeye.getReflections().getNMSClass("AxisAlignedBB").getFieldByName(value).get(axisAlignedBB);
    }

    public Set<Block> getSolidBlocks() {
        Set<Block> blocks = Sets.newHashSet();
        for (Object object : ((Collection<?>) this.autoeye.getReflections().getNMSClass("World").getMethod("a", this.axisAlignedBB.getClass()).invoke(this.world, this.axisAlignedBB))) {
            blocks.add(this.bukkitWorld.getBlockAt((int) this.get(object, "a"), (int) this.get(object, "b"), (int) this.get(object, "c")));
        }
        return blocks;
    }

    public boolean containsMaterial(String materialName) {
        return (boolean) this.autoeye.getReflections().getNMSClass("World").getMethod("a", this.axisAlignedBB.getClass(), this.autoeye.getReflections().getNMSClass("Material").getParent()).invoke(this.world, this.axisAlignedBB, this.autoeye.getReflections().getNMSClass("Material").getFieldByName(materialName).get(null));
    }
}
