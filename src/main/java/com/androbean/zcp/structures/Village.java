package com.androbean.zcp.structures;

import com.androbean.zcp.entity.EntityPepoVillager;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Village {
    private final String FILE_PATH;
    public World world;
    public int population;
    public List<EntityPepoVillager> populous;

    public Village(World world, String name) throws IOException {
        this.FILE_PATH = new File(world.getMinecraftServer().getDataDirectory().getAbsolutePath().replaceAll("\\\\\\.$", "") + (world.getMinecraftServer().isSinglePlayer() ? "\\saves\\" : "\\") + world.getMinecraftServer().getFolderName() + "\\" + name +".dat").getAbsolutePath();
        this.load();
        this.world = world;
    }

    public void load() {
        try {
            File file = new File(this.FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                this.save();
            }
            else {
                FileInputStream stream = new FileInputStream(file);
                NBTTagCompound nbt = CompressedStreamTools.readCompressed(stream);
                this.readFromNBT(nbt);
                stream.close();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            File file = new File(this.FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            NBTTagCompound nbt = this.writeToNBT(new NBTTagCompound());
            CompressedStreamTools.writeCompressed(nbt, stream);
            stream.flush();
            stream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void readFromNBT(NBTTagCompound nbt) {
        if(this.populous != null) {
            NBTTagList list = nbt.getTagList("populous", 10);
            for (int i = 0; i < list.tagCount(); i++){
                NBTTagCompound tag = list.getCompoundTagAt(i);
                EntityPepoVillager pepo = new EntityPepoVillager(this.world);
                pepo.readFromNBT(tag);
                this.populous.add(pepo);
            }
        }
        this.population = nbt.getInteger("population");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        if(this.populous != null){
            for(EntityPepoVillager pepo : this.populous){
                NBTTagCompound tag = new NBTTagCompound();
                pepo.writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        compound.setTag("populous", list);
        compound.setInteger("population", this.population);
        return compound;
    }
}
