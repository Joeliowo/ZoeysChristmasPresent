package com.androbean.zcp.init;

import mod.akrivus.kagic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ZModCreativeTabs extends CreativeTabs {
    public static final CreativeTabs CREATIVE_TAB_PEPO_SEEDS = new ZModCreativeTabs("pepo_seeds", 0);
    public static final CreativeTabs CREATIVE_TAB_ANIMAL_SEEDS = new ZModCreativeTabs("animal_seeds", 1);
    public static final CreativeTabs CREATIVE_TAB_PEPO_FRUIT = new ZModCreativeTabs("pepo_fruit", 2);
    public static final CreativeTabs CREATIVE_TAB_ANIMAL_FRUIT = new ZModCreativeTabs("animal_fruit", 3);
    //public static final CreativeTabs CREATIVE_TAB_OTHER = new ZModCreativeTabs("other", 4);
    private final int id;

    public ZModCreativeTabs(String label, int id) {
        super(CreativeTabs.getNextID(), label);
        this.id = id;
    }

    @Override
    public ItemStack getTabIconItem() {
        switch (this.id) {
            case 0:
                return new ItemStack(ZModItems.MELON_SEEDS);
            case 1:
                return new ItemStack(ZModItems.CHICKEN_SEEDS);
            case 2:
                return new ItemStack(ZModItems.PEPO_MELON);
            case 3:
                return new ItemStack(ZModItems.ANIMAL_MELON);
            case 4:
                return new ItemStack(ZModBlocks.MELON_STEM);
        }
        return new ItemStack(ModItems.CRACKED_YELLOW_DIAMOND_GEM);
    }
}
