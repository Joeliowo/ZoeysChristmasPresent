package com.androbean.zcp.entity;

import com.androbean.zcp.entity.ai.EntityAIHarvestBabies;
import com.androbean.zcp.init.ZModBlocks;
import com.androbean.zcp.init.ZModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMelonChicken extends EntityPepoAnimal {
    public int eggCounter;

    public EntityMelonChicken(World world){
        super(world);
        if(this.getAge() == 0) {
            this.setSize(.25f, .25f);
        }
        else{
            this.setSize(.5F, .8F);
        }
        this.tasks.addTask(5, new EntityAIWander(this, .8D));
        this.tasks.addTask(3, new EntityAIPanic(this, 1.0D));
        this.tasks.addTask(3, new EntityAIEatGrass(this));
        this.tasks.addTask(5, new EntityAIHarvestBabies(this, .8D));
        if(this.getAge() > 0) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        }
        else{
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        }
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.8D);
        this.seedItem = ZModItems.CHICKEN_SEEDS;
        this.dropItem = ZModItems.ANIMAL_MELON;
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("egg", this.eggCounter);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.eggCounter = compound.getInteger("egg");
        if(this.getAge() > 0){
            this.setSize(.5F, .8F);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        }
    }

    @Override
    public void onLivingUpdate(){
        if(this.eggCounter > 3000){
            if(this.getAge() == 1) {
                if (this.world.getBlockState(this.getPosition().down()) == Blocks.GRASS.getDefaultState()) {
                    this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1, 1);
                    this.world.setBlockState(this.getPosition(), ZModBlocks.CHICKEN_STEM.getDefaultState());
                } else {
                    this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1, 1);
                    this.entityDropItem(new ItemStack(ZModItems.CHICKEN_SEEDS), 0);
                }
            }
            this.eggCounter = 0;
        }
        this.eggCounter++;
        super.onLivingUpdate();
    }

    @Override
    public void onAgeUp(){
        super.onAgeUp();
        if(this.getAge() == 1){
            this.setSize(.5f, .8f);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        }
    }
}
