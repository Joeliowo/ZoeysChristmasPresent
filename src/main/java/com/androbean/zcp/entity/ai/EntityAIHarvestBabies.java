package com.androbean.zcp.entity.ai;

import com.androbean.zcp.blocks.BlockMelonChickenStem;
import com.androbean.zcp.entity.EntityMelonChicken;
import com.androbean.zcp.entity.EntityPepoAnimal;
import com.androbean.zcp.init.ZModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIHarvestBabies extends EntityAIMoveToBlock {
    private final EntityPepoAnimal pepo;
    private final World world;
    private int currentTask = -1;
    private int delay = 0;

    public EntityAIHarvestBabies(EntityPepoAnimal gemIn, double speedIn) {
        super(gemIn, speedIn, 16);
        this.pepo = gemIn;
        this.world = gemIn.world;
    }

    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && this.pepo.getAge() == 1 && !this.pepo.getNavigator().noPath();
    }

    public void startExecuting() {
        super.startExecuting();
    }

    public void resetTask() {
        super.resetTask();
    }

    public boolean shouldExecute() {
        if (this.pepo.getAge() == 1) {
            if (delay > 20 + this.pepo.getRNG().nextInt(20)) {
                this.runDelay = 0;
                return super.shouldExecute();
            }
            else {
                ++this.delay;
            }
        }
        return false;
    }

    public void updateTask() {
        super.updateTask();
        this.pepo.getLookHelper().setLookPosition((double) this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double) this.destinationBlock.getZ() + 0.5D, 10.0F, (float) this.pepo.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            BlockPos blockpos = this.destinationBlock.up();
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (this.currentTask == 0 && block == pepo.stem && block.getMetaFromState(iblockstate) == 7) {
                this.world.destroyBlock(blockpos, true);
            }
            this.currentTask = -1;
        }
    }

    @Override
    protected boolean shouldMoveTo(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        BlockPos cropPos = pos.up();
        IBlockState iblockstate = world.getBlockState(cropPos);
        Block crop = iblockstate.getBlock();
        if(block == Blocks.GRASS) {
            if (crop == pepo.stem && crop.getMetaFromState(iblockstate) == 7) {
                this.currentTask = 0;
                return true;
            }
        }
        return false;
    }
}
