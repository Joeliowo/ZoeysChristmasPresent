package com.androbean.zcp.init;

import com.androbean.zcp.items.ItemMelon;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.event.RegistryEvent;

public class ZModItems {
    public static final ItemMelon ANIMAL_MELON = (ItemMelon) new ItemMelon(1, 1, true, "animal").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_FRUIT);
    public static final ItemMelon PEPO_MELON = (ItemMelon) new ItemMelon(1, 1, true, "person").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_FRUIT);
    public static final ItemSeeds MELON_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.MELON_STEM, Blocks.FARMLAND).setUnlocalizedName("melon_pepo_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_PEPO_SEEDS);
    public static final ItemSeeds CHICKEN_SEEDS = (ItemSeeds) new ItemSeeds(ZModBlocks.CHICKEN_STEM, Blocks.GRASS).setUnlocalizedName("melon_chicken_seeds").setCreativeTab(ZModCreativeTabs.CREATIVE_TAB_ANIMAL_SEEDS);

    public static void registerItems(RegistryEvent.Register<Item> event){
        ModItems.registerExternalItem(ANIMAL_MELON, "zcp", event);
        ModItems.registerExternalItem(PEPO_MELON, "zcp", event);
        ModItems.registerExternalItem(MELON_SEEDS, "zcp", event);
        ModItems.registerExternalItem(CHICKEN_SEEDS, "zcp", event);
    }
}
 