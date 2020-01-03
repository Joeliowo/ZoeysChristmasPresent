package com.androbean.zcp.init;

import com.androbean.zcp.items.ItemFruit;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.event.RegistryEvent;

public class ZModItems {
    public static final ItemFruit ANIMAL_MELON = (ItemFruit) new ItemFruit(1, 1, true, "melon", "animal").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_FRUIT);
    public static final ItemFruit PEPO_MELON = (ItemFruit) new ItemFruit(1, 1, true, "melon", "person").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_FRUIT);
    public static final ItemSeeds MELON_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.MELON_STEM, Blocks.FARMLAND).setUnlocalizedName("melon_pepo_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_SEEDS);
    public static final ItemSeeds CHICKEN_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.CHICKEN_STEM, Blocks.GRASS).setUnlocalizedName("melon_chicken_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_SEEDS);

    public static final ItemFruit ANIMAL_PUMPKIN= (ItemFruit) new ItemFruit(1, 1, true, "pumpkin", "animal").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_FRUIT);
    public static final ItemFruit PEPO_PUMPKIN = (ItemFruit) new ItemFruit(1, 1, true, "pumpkin", "person").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_FRUIT);
    public static final ItemSeeds PUMPKIN_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.PUMPKIN_STEM, Blocks.FARMLAND).setUnlocalizedName("pumpkin_pepo_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_SEEDS);
    public static final ItemSeeds PUMPKIN_CHICKEN_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.PUMPKIN_CHICKEN_STEM, Blocks.GRASS).setUnlocalizedName("pumpkin_chicken_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_SEEDS);

    public static final ItemFruit ANIMAL_WHEAT = (ItemFruit) new ItemFruit(1, 1, true, "wheat", "animal").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_FRUIT);
    public static final ItemFruit PEPO_WHEAT = (ItemFruit) new ItemFruit(1, 1, true, "wheat", "person").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_FRUIT);
    public static final ItemSeeds WHEAT_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.WHEAT_STEM, Blocks.FARMLAND).setUnlocalizedName("wheat_pepo_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_SEEDS);
    public static final ItemSeeds WHEAT_CHICKEN_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.WHEAT_CHICKEN_STEM, Blocks.GRASS).setUnlocalizedName("wheat_chicken_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_SEEDS);

    public static final ItemFruit ANIMAL_POTATO = (ItemFruit) new ItemFruit(1, 1, true, "potato", "animal").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_FRUIT);
    public static final ItemFruit PEPO_POTATO = (ItemFruit) new ItemFruit(1, 1, true, "potato", "person").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_FRUIT);
    public static final ItemSeeds POTATO_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.POTATO_STEM, Blocks.FARMLAND).setUnlocalizedName("potato_pepo_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_SEEDS);
    public static final ItemSeeds POTATO_CHICKEN_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.POTATO_CHICKEN_STEM, Blocks.GRASS).setUnlocalizedName("potato_chicken_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_SEEDS);

    public static final ItemFruit ANIMAL_CARROT = (ItemFruit) new ItemFruit(1, 1, true, "carrot", "animal").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_FRUIT);
    public static final ItemFruit PEPO_CARROT = (ItemFruit) new ItemFruit(1, 1, true, "carrot", "person").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_FRUIT);
    public static final ItemSeeds CARROT_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.CARROT_STEM, Blocks.FARMLAND).setUnlocalizedName("carrot_pepo_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_SEEDS);
    public static final ItemSeeds CARROT_CHICKEN_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.CARROT_CHICKEN_STEM, Blocks.GRASS).setUnlocalizedName("carrot_chicken_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_SEEDS);

    public static void registerItems(RegistryEvent.Register<Item> event){
        ModItems.registerExternalItem(ANIMAL_MELON, "zcp", event);
        ModItems.registerExternalItem(PEPO_MELON, "zcp", event);
        ModItems.registerExternalItem(MELON_SEEDS, "zcp", event);
        ModItems.registerExternalItem(CHICKEN_SEEDS, "zcp", event);

        ModItems.registerExternalItem(ANIMAL_PUMPKIN, "zcp", event);
        ModItems.registerExternalItem(PEPO_PUMPKIN, "zcp", event);
        ModItems.registerExternalItem(PUMPKIN_SEEDS, "zcp", event);
        ModItems.registerExternalItem(PUMPKIN_CHICKEN_SEEDS, "zcp", event);

        ModItems.registerExternalItem(ANIMAL_POTATO, "zcp", event);
        ModItems.registerExternalItem(PEPO_POTATO, "zcp", event);
        ModItems.registerExternalItem(POTATO_SEEDS, "zcp", event);
        ModItems.registerExternalItem(POTATO_CHICKEN_SEEDS, "zcp", event);

        ModItems.registerExternalItem(ANIMAL_WHEAT, "zcp", event);
        ModItems.registerExternalItem(PEPO_WHEAT, "zcp", event);
        ModItems.registerExternalItem(WHEAT_SEEDS, "zcp", event);
        ModItems.registerExternalItem(WHEAT_CHICKEN_SEEDS, "zcp", event);

        ModItems.registerExternalItem(ANIMAL_CARROT, "zcp", event);
        ModItems.registerExternalItem(PEPO_CARROT, "zcp", event);
        ModItems.registerExternalItem(CARROT_SEEDS, "zcp", event);
        ModItems.registerExternalItem(CARROT_CHICKEN_SEEDS, "zcp", event);
    }
}
 