package com.androbean.zcp.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemFruit extends ItemFood {

    public ItemFruit(int amount, float saturation, boolean isWolfFood, String fruit, String species) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(fruit + "_" + species);
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setMaxStackSize(64);
    }
}
