package com.androbean.zcp.entity;

import com.androbean.zcp.entity.ai.EntityAIHarvestAnimals;
import com.androbean.zcp.entity.ai.EntityAIPickUpItems;
import com.androbean.zcp.init.ZModBlocks;
import com.androbean.zcp.init.ZModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCarrotPepo extends EntityPepoVillager {

    public EntityCarrotPepo(World worldIn) {
        super(worldIn);
        if(this.getAge() == 0){
            this.setSize(0.3F, 0.8F);
        }
        else{
            this.setSize(0.5F, 1.5F);
        }
        this.initStorage();
        this.setCanPickUpLoot(true);
        //Hey Cillian tomorrow, remember that the problem is probably in the job the pepo has since u cant be bothered to wait for a pepo to grow up.
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(9, new EntityAIHarvestAnimals(this, 1.0D));
        this.tasks.addTask(3, new EntityAIPickUpItems(this, 1.0D));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        if(this.getAge() > 0) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        }
        else{
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        }
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        this.dropItem = ZModItems.PEPO_CARROT;
        this.eatItem = ZModItems.ANIMAL_CARROT;
        this.seedItem = ZModItems.CARROT_SEEDS;
        this.fruitSeed = ZModItems.CARROT_CHICKEN_SEEDS;
        this.fruitStem = ZModBlocks.CARROT_CHICKEN_STEM;
    }

    public void readEntityFromNBT(NBTTagCompound tag){
        super.readEntityFromNBT(tag);
        if(this.getAge() > 0){
            this.setSize(.5f, 1.5f);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20F);
        }
    }

    @Override
    public void onAgeUp(){
        super.onAgeUp();
        if(this.getAge() == 1) {
            this.setSize(.5f, 1.5f);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20F);
        }
    }
}
