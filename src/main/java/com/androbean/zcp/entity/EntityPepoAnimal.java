package com.androbean.zcp.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityPepoAnimal extends EntityCreature implements IEntityAdditionalSpawnData {
    protected static final DataParameter<Integer> AGE = EntityDataManager.<Integer>createKey(EntityPepoAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> MAX_AGE = EntityDataManager.<Integer>createKey(EntityPepoAnimal.class, DataSerializers.VARINT);
    public static final int ROTTING_SKIN_COLOR = 0xE0C0A4;
    public Item seedItem;
    public Item dropItem;
    public Block stem;
    public int ageTicks = 0;

    public EntityPepoAnimal(World worldIn) {
        super(worldIn);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.dataManager.register(AGE, 0);
        this.dataManager.register(MAX_AGE, 3);
    }

    @Override
    public void onLivingUpdate(){
        if(this.ageTicks >= this.growthStage()){
            this.setAge(this.getAge() + 1);
            this.ageTicks = 0;
            this.onAgeUp();
            if(this.getAge() >= this.getMaxAge()){
                this.setHealth(0);
            }
        }
        this.ageTicks++;
        super.onLivingUpdate();
    }

    public int growthStage(){
        switch (this.getAge()){
            case 0:
                return 6000; // 6000
            case 1:
                return 9000; // 9000
            case 2:
                return 9000; // 9000
        }
        return 9000;
    }

    public void onAgeUp(){

    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("ageTicks", this.ageTicks);
        compound.setInteger("age", this.getAge());
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.ageTicks = compound.getInteger("ageTicks");
        this.setAge(compound.getInteger("age"));
    }

    public int getSkinColor(){
        if(this.getAge() == this.getMaxAge() - 1){
            return ROTTING_SKIN_COLOR;
        }
        else{
            return 0xFFFFFF;
        }
    }

    public void onDeath(DamageSource cause) {
        ItemStack drop = new ItemStack(this.dropItem);
        if (!this.world.isRemote) {
            if(this.getAge() != 3) {
                this.entityDropItem(drop, 0.0F);
                this.entityDropItem(new ItemStack(this.seedItem, this.rand.nextInt(3)), 0.0F);
            }
        }
        super.onDeath(cause);
    }

    public int getAge(){
        return this.dataManager.get(AGE);
    }

    public void setAge(int value){
        this.dataManager.set(AGE, value);
    }

    public int getMaxAge(){
        return this.dataManager.get(MAX_AGE);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {

    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {

    }
}
