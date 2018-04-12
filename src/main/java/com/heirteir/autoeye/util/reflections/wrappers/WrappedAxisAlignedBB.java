package com.heirteir.autoeye.util.reflections.wrappers;

import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor public class WrappedAxisAlignedBB {
    private final Autoeye autoeye;
    private final World bukkitWorld;
    private final Object world;
    private final Object axisAlignedBB;
    private final float minX, minY, minZ, maxX, maxY, maxZ;

    private WrappedAxisAlignedBB(Autoeye autoeye, World world, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.autoeye = autoeye;
        this.bukkitWorld = world;
        this.world = autoeye.getReflections().getCBClass("CraftWorld").getMethod("getHandle").invoke(world);
        this.axisAlignedBB = autoeye.getReflections().getNMSClass("AxisAlignedBB").getConstructor(double.class, double.class, double.class, double.class, double.class, double.class).newInstance(this.minX = minX, this.minY = minY, this.minZ = minZ, this.maxX = maxX, this.maxY = maxY, this.maxZ = maxZ);
    }

    public WrappedAxisAlignedBB(Autoeye autoeye, World world, float x, float y, float z, float width, float height) {
        this(autoeye, world, x - width / 2, y, z - width / 2, x + width / 2, y + height, z + width / 2);
    }

    public WrappedAxisAlignedBB offset(float x, float y, float z, float x2, float y2, float z2) {
        return new WrappedAxisAlignedBB(this.autoeye, this.bukkitWorld, this.minX + x, this.minY + y, this.minZ + z, this.maxX + x2, this.maxY + y2, this.maxZ + z2);
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
        return (boolean) this.autoeye.getReflections().getNMSClass("World").getMethodByTypes(this.axisAlignedBB.getClass(), this.autoeye.getReflections().getNMSClass("Material").getParent()).invoke(this.world, this.axisAlignedBB, this.autoeye.getReflections().getNMSClass("Material").getFieldByName(materialName).get(null));
    }

    public boolean containsLiquid() {
        return this.autoeye.getReflections().getNMSClass("World").getMethod("containsLiquid", autoeye.getReflections().getNMSClass("AxisAlignedBB").getParent()).invoke(this.world, this.axisAlignedBB);
    }
}
