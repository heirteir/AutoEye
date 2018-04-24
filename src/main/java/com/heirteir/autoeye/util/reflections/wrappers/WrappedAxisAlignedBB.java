/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.reflections.wrappers;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import com.heirteir.autoeye.util.reflections.types.WrappedConstructor;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import com.heirteir.autoeye.util.reflections.types.WrappedMethod;
import com.heirteir.autoeye.util.server.Version;
import com.heirteir.autoeye.util.vector.Vector3D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor public class WrappedAxisAlignedBB {
    private static final WrappedClass materialClass = Reflections.getNMSClass("Material");
    static final WrappedMethod getHandleMethod = Reflections.getCBClass("CraftWorld").getMethod("getHandle");
    private static final WrappedMethod getMaterialsMethod = Reflections.getNMSClass("World").getMethod("a", Reflections.getNMSClass("AxisAlignedBB").getParent(), materialClass.getParent());
    private static final WrappedMethod containsLiquidMethod = Reflections.getNMSClass("World").getMethod("containsLiquid", Reflections.getNMSClass("AxisAlignedBB").getParent());
    private static final WrappedConstructor axisAlignedBBConstructor = Reflections.getNMSClass("AxisAlignedBB").getConstructor(double.class, double.class, double.class, double.class, double.class, double.class);
    private static final WrappedField minXField = Reflections.getNMSClass("AxisAlignedBB").getFieldByName("a");
    private static final WrappedField minYField = Reflections.getNMSClass("AxisAlignedBB").getFieldByName("b");
    private static final WrappedField minZField = Reflections.getNMSClass("AxisAlignedBB").getFieldByName("c");
    private static final WrappedField maxXField = Reflections.getNMSClass("AxisAlignedBB").getFieldByName("d");
    private static final WrappedField maxYField = Reflections.getNMSClass("AxisAlignedBB").getFieldByName("e");
    private static final WrappedField maxZField = Reflections.getNMSClass("AxisAlignedBB").getFieldByName("f");
    private static final WrappedMethod getCubesMethod;
    private final Autoeye autoeye;
    private final World bukkitWorld;
    private final Object world;
    @Getter private final Object axisAlignedBB;
    @Getter private final Vector3D min, max;

    static {
        if (Autoeye.getVersion().equals(Version.ELEVEN) || Autoeye.getVersion().equals(Version.TWELVE)) {
            getCubesMethod = Reflections.getNMSClass("World").getMethod("getCubes", Reflections.getNMSClass("Entity").getParent(), Reflections.getNMSClass("AxisAlignedBB").getParent());
        } else {
            getCubesMethod = Reflections.getNMSClass("World").getMethod("a", Reflections.getNMSClass("AxisAlignedBB").getParent());
        }
    }

    private List<Object> boundingBoxes = null;

    WrappedAxisAlignedBB(Autoeye autoeye, World world, Object axisAlignedBB) {
        this.autoeye = autoeye;
        this.bukkitWorld = world;
        this.world = getHandleMethod.invoke(world);
        this.min = new Vector3D(((Double) minXField.get(axisAlignedBB)).floatValue(), ((Double) minYField.get(axisAlignedBB)).floatValue(), ((Double) minZField.get(axisAlignedBB)).floatValue());
        this.max = new Vector3D(((Double) maxXField.get(axisAlignedBB)).floatValue(), ((Double) maxYField.get(axisAlignedBB)).floatValue(), ((Double) maxZField.get(axisAlignedBB)).floatValue());
        this.axisAlignedBB = axisAlignedBBConstructor.newInstance(this.min.getX(), this.min.getY(), this.min.getZ(), this.max.getX(), this.max.getY(), this.max.getZ());
    }

    private WrappedAxisAlignedBB(Autoeye autoeye, World world, Vector3D min, Vector3D max) {
        this.autoeye = autoeye;
        this.bukkitWorld = world;
        this.world = getHandleMethod.invoke(world);
        this.min = min;
        this.max = max;
        this.axisAlignedBB = axisAlignedBBConstructor.newInstance(this.min.getX(), this.min.getY(), this.min.getZ(), this.max.getX(), this.max.getY(), this.max.getZ());
    }

    public WrappedAxisAlignedBB(Autoeye autoeye, World world, float x, float y, float z, float width, float height) {
        this(autoeye, world, new Vector3D(x - width / 2, y, z - width / 2), new Vector3D(x + width / 2, y + height, z + width / 2));
    }

    public WrappedAxisAlignedBB offset(float x, float y, float z, float x2, float y2, float z2) {
        return new WrappedAxisAlignedBB(this.autoeye, this.bukkitWorld, this.min.offset(x, y, z), this.max.offset(x2, y2, z2));
    }

    private List<Object> getBoundingBoxes() {
        return this.boundingBoxes == null ? this.boundingBoxes = getCubesMethod.getParameters().size() == 1 ? Lists.newArrayList(((Collection<?>) getCubesMethod.invoke(this.world, this.axisAlignedBB))) : Lists.newArrayList(((Collection<?>) getCubesMethod.invoke(this.world, null, this.axisAlignedBB))) : this.boundingBoxes;
    }

    public Set<Block> getSolidBlocks() {
        Set<Block> blocks = Sets.newHashSet();
        for (Object object : this.getBoundingBoxes()) {
            blocks.add(this.bukkitWorld.getBlockAt(((Double) minXField.get(object)).intValue(), ((Double) minYField.get(object)).intValue(), ((Double) minZField.get(object)).intValue()));
        }
        return blocks;
    }

    public boolean containsMaterial(String materialName) {
        return (boolean) getMaterialsMethod.invoke(this.world, this.axisAlignedBB, materialClass.getFieldByName(materialName).get(null));
    }

    public boolean containsLiquid() {
        return containsLiquidMethod.invoke(this.world, this.axisAlignedBB);
    }
}
