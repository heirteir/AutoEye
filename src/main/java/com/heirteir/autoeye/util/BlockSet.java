/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:46 PM
 */
package com.heirteir.autoeye.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;

import java.util.Set;

@RequiredArgsConstructor @Getter public class BlockSet {
    private final Set<Block> blocks;

    public boolean containsString(String... values) {
        for (Block block : this.blocks) {
            for (String value : values) {
                if (block.getType().name().contains(value)) {
                    return true;
                }
            }
        }
        return false;
    }
}
