package com.androbean.zcp.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemMelon extends ItemFood {

    public ItemMelon(int amount, float saturation, boolean isWolfFood, String species) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName("melon_" + species);
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setMaxStackSize(64);
    }
}
