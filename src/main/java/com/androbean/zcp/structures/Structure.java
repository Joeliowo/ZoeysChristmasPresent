package com.androbean.zcp.structures;

import com.androbean.zcp.entity.EntityPepoVillager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Structure{
    public StructureData structureData;
    public EntityPepoVillager owner;
    public World world;

    public Structure(String name, World world){
        this.structureData = SchematicLoader.loadSchematic(name);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound.setString("name", this.structureData.getName());
        compound.setTag("owner", this.getOwner().writeToNBT(compound));
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound){
        EntityPepoVillager pepo = new EntityPepoVillager(this.world);
        pepo.readFromNBT(compound.getCompoundTag("owner"));
        this.setOwner(pepo);
    }

    public void setOwner(EntityPepoVillager owner){
        this.owner = owner;
    }

    public EntityPepoVillager getOwner() {
        return this.owner;
    }
}
