package com.androbean.zcp.structures;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class SchematicLoading {

    public SchematicLoading(){

    }

    public void SpawnSchematic(StructureData schematic, EntityLivingBase entity){
        BlockPos ePos = entity.getPosition();
        for (BlockPos offset : schematic.structureBlocks.keySet()) {
            if (schematic.structureBlocks.get(offset).getBlock() == Blocks.AIR || schematic.structureBlocks.get(offset).getBlock() == Blocks.STRUCTURE_VOID) {
                continue;
            }
            entity.world.setBlockState(ePos.add(offset), schematic.structureBlocks.get(offset));
        }
    }
}
