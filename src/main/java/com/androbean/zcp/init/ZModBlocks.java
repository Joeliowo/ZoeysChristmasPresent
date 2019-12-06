package com.androbean.zcp.init;

import com.androbean.zcp.blocks.BlockMelonChickenStem;
import com.androbean.zcp.blocks.BlockMelonPepoStem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class ZModBlocks {
    public static final BlockMelonPepoStem MELON_STEM = new BlockMelonPepoStem(Blocks.AIR);
    public static final BlockMelonChickenStem CHICKEN_STEM = new BlockMelonChickenStem(Blocks.AIR);

    public static void registerBlocks(RegistryEvent.Register<Block> event){
        registerBlock(MELON_STEM, new ResourceLocation("zcp:melon_stem"), event);
        registerBlock(CHICKEN_STEM, new ResourceLocation("zcp:chicken_stem"), event);
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event){
        registerBlockItem(MELON_STEM, new ResourceLocation("zcp:melon_stem"), event, "blockPepoMelonStem");
        registerBlockItem(CHICKEN_STEM, new ResourceLocation("zcp:chicken_stem"), event, "blockChickenMelonStem");
    }

    public static void registerBlock(Block block, ResourceLocation location, RegistryEvent.Register<Block> event) {
        block.setRegistryName(location);
        event.getRegistry().register(block);
    }

    public static void registerBlockItem(Block block, ResourceLocation location, RegistryEvent.Register<Item> event, String oredictName) {
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(location);
        event.getRegistry().register(item);
        if (!oredictName.isEmpty()) {
            OreDictionary.registerOre(oredictName, item);
        }
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
