package com.androbean.zcp.blocks;

import com.androbean.zcp.entity.EntityMelonPepo;
import com.androbean.zcp.init.ZModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMelonPepoStem extends BlockStem {

    public BlockMelonPepoStem(Block crop) {
        super(crop);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        drops.add(new ItemStack(ZModItems.MELON_SEEDS));
        if(this.getMetaFromState(state) == 7) {
            EntityMelonPepo pepo = new EntityMelonPepo((World) world);
            pepo.setLocationAndAngles(0.5D + (double) pos.getX(), 0.05D + (double) pos.getY(), 0.5D + (double) pos.getZ(), 0.0F, 0.0F);
            ((World) world).spawnEntity(pepo);
            drops.add(new ItemStack(ZModItems.MELON_SEEDS, 2));
        }
    }
}
