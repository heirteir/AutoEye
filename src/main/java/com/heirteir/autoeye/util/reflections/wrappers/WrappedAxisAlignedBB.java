package com.heirteir.autoeye.util.reflections.wrappers;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.vector.Ray3D;
import com.heirteir.autoeye.util.vector.Vector3D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor public class WrappedAxisAlignedBB {
    private final Autoeye autoeye;
    private final World bukkitWorld;
    private final Object world;
    @Getter private final Object axisAlignedBB;
    @Getter private final Vector3D min, max;

    public WrappedAxisAlignedBB(Autoeye autoeye, World world, Vector3D vector3D) {
        this.autoeye = autoeye;
        this.bukkitWorld = world;
        this.world = autoeye.getReflections().getCBClass("CraftWorld").getMethod("getHandle").invoke(world);
        List<Object> boundingBoxes = new WrappedAxisAlignedBB(autoeye, world, vector3D, vector3D.offset(1, 0.06F, 1)).getBoundingBoxes();
        if (boundingBoxes.size() > 0) {
            this.axisAlignedBB = boundingBoxes.get(0);
            this.min = new Vector3D((float) this.get("a"), (float) this.get("b"), (float) this.get("c"));
            this.max = new Vector3D((float) this.get("d"), (float) this.get("e"), (float) this.get("f"));
        } else {
            this.axisAlignedBB = null;
            this.min = this.max = new Vector3D(0, 0, 0);
        }
    }

    public WrappedAxisAlignedBB(Autoeye autoeye, World world, Vector3D min, Vector3D max) {
        this.autoeye = autoeye;
        this.bukkitWorld = world;
        this.world = autoeye.getReflections().getCBClass("CraftWorld").getMethod("getHandle").invoke(world);
        this.min = min;
        this.max = max;
        this.axisAlignedBB = autoeye.getReflections().getNMSClass("AxisAlignedBB").getConstructor(double.class, double.class, double.class, double.class, double.class, double.class).newInstance(this.min.getX(), this.min.getY(), this.min.getZ(), this.max.getX(), this.max.getY(), this.max.getZ());
    }

    public WrappedAxisAlignedBB(Autoeye autoeye, World world, float x, float y, float z, float width, float height) {
        this(autoeye, world, new Vector3D(x - width / 2, y, z - width / 2), new Vector3D(x + width / 2, y + height, z + width / 2));
    }

    public WrappedAxisAlignedBB offset(float x, float y, float z, float x2, float y2, float z2) {
        return new WrappedAxisAlignedBB(this.autoeye, this.bukkitWorld, this.min.offset(x, y, z), this.max.offset(x2, y2, z2));
    }

    private double get(String value) {
        return this.get(this.axisAlignedBB, value);
    }

    private double get(Object axisAlignedBB, String value) {
        return (double) this.autoeye.getReflections().getNMSClass("AxisAlignedBB").getFieldByName(value).get(axisAlignedBB);
    }

    private List<Object> getBoundingBoxes() {
        return Lists.newArrayList(((Collection<?>) this.autoeye.getReflections().getNMSClass("World").getMethod("a", this.axisAlignedBB.getClass()).invoke(this.world, this.axisAlignedBB)));
    }

    public Set<Block> getSolidBlocks() {
        Set<Block> blocks = Sets.newHashSet();
        for (Object object : this.getBoundingBoxes()) {
            blocks.add(this.bukkitWorld.getBlockAt((int) this.get(object, "a"), (int) this.get(object, "b"), (int) this.get(object, "c")));
        }
        return blocks;
    }

    public boolean containsMaterial(String materialName) {
        return (boolean) this.autoeye.getReflections().getNMSClass("World").getMethod("a", this.axisAlignedBB.getClass(), this.autoeye.getReflections().getNMSClass("Material").getParent()).invoke(this.world, this.axisAlignedBB, this.autoeye.getReflections().getNMSClass("Material").getFieldByName(materialName).get(null));
    }

    public boolean containsLiquid() {
        return this.autoeye.getReflections().getNMSClass("World").getMethod("containsLiquid", autoeye.getReflections().getNMSClass("AxisAlignedBB").getParent()).invoke(this.world, this.axisAlignedBB);
    }

    public boolean intersectsRay(Ray3D ray) {
        Vector3D fraction = new Vector3D(1.0F / ray.getDirection().getX(), 1.0F / ray.getDirection().getY(), 1.0F / ray.getDirection().getZ());
        float t1 = (min.getX() - ray.getX()) * fraction.getX();
        float t2 = (max.getX() - ray.getX()) * fraction.getX();
        float t3 = (min.getY() - ray.getY()) * fraction.getY();
        float t4 = (max.getY() - ray.getY()) * fraction.getY();
        float t5 = (min.getZ() - ray.getZ()) * fraction.getZ();
        float t6 = (max.getZ() - ray.getZ()) * fraction.getZ();
        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));
        return !(tmax < 0) && !(tmin > tmax);
    }
}
