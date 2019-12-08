package com.androbean.zcp.init;

import com.androbean.zcp.blocks.*;
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

    public static final BlockPotatoPepoStem POTATO_STEM = new BlockPotatoPepoStem(Blocks.AIR);
    public static final BlockPotatoChickenStem POTATO_CHICKEN_STEM = new BlockPotatoChickenStem(Blocks.AIR);

    public static final BlockCarrotPepoStem CARROT_STEM = new BlockCarrotPepoStem(Blocks.AIR);
    public static final BlockCarrotChickenStem CARROT_CHICKEN_STEM = new BlockCarrotChickenStem(Blocks.AIR);

    public static final BlockWheatPepoStem WHEAT_STEM = new BlockWheatPepoStem(Blocks.AIR);
    public static final BlockWheatChickenStem WHEAT_CHICKEN_STEM = new BlockWheatChickenStem(Blocks.AIR);

    public static final BlockPumpkinPepoStem PUMPKIN_STEM = new BlockPumpkinPepoStem(Blocks.AIR);
    public static final BlockPumpkinChickenStem PUMPKIN_CHICKEN_STEM = new BlockPumpkinChickenStem(Blocks.AIR);


    public static void registerBlocks(RegistryEvent.Register<Block> event){
        registerBlock(MELON_STEM, new ResourceLocation("zcp:melon_stem"), event);
        registerBlock(CHICKEN_STEM, new ResourceLocation("zcp:chicken_stem"), event);

        registerBlock(POTATO_STEM, new ResourceLocation("zcp:potato_stem"), event);
        registerBlock(POTATO_CHICKEN_STEM, new ResourceLocation("zcp:potato_chicken_stem"), event);

        registerBlock(CARROT_STEM, new ResourceLocation("zcp:carrot_stem"), event);
        registerBlock(CARROT_CHICKEN_STEM, new ResourceLocation("zcp:carrot_chicken_stem"), event);

        registerBlock(WHEAT_STEM, new ResourceLocation("zcp:wheat_stem"), event);
        registerBlock(WHEAT_CHICKEN_STEM, new ResourceLocation("zcp:wheat_chicken_stem"), event);

        registerBlock(PUMPKIN_STEM, new ResourceLocation("zcp:pumpkin_stem"), event);
        registerBlock(PUMPKIN_CHICKEN_STEM, new ResourceLocation("zcp:pumpkin_chicken_stem"), event);
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event){
        registerBlockItem(MELON_STEM, new ResourceLocation("zcp:melon_stem"), event, "blockPepoMelonStem");
        registerBlockItem(CHICKEN_STEM, new ResourceLocation("zcp:chicken_stem"), event, "blockChickenMelonStem");

        registerBlockItem(POTATO_STEM, new ResourceLocation("zcp:potato_stem"), event, "blockPepoPotatoStem");
        registerBlockItem(POTATO_CHICKEN_STEM, new ResourceLocation("zcp:potato_chicken_stem"), event, "blockChickenPotatoStem");

        registerBlockItem(CARROT_STEM, new ResourceLocation("zcp:carrot_stem"), event, "blockPepoCarrotStem");
        registerBlockItem(CARROT_CHICKEN_STEM, new ResourceLocation("zcp:carrot_chicken_stem"), event, "blockChickenCarrotStem");

        registerBlockItem(WHEAT_STEM, new ResourceLocation("zcp:wheat_stem"), event, "blockPepoWheatStem");
        registerBlockItem(WHEAT_CHICKEN_STEM, new ResourceLocation("zcp:wheat_chicken_stem"), event, "blockChickenWheatStem");

        registerBlockItem(PUMPKIN_STEM, new ResourceLocation("zcp:pumpkin_stem"), event, "blockPepoPumpkinStem");
        registerBlockItem(PUMPKIN_CHICKEN_STEM, new ResourceLocation("zcp:pumpkin_chicken_stem"), event, "blockChickenPumpkinStem");
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
