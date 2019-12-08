package com.androbean.zcp.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemFruit extends ItemFood {

    public ItemFruit(int amount, float saturation, boolean isWolfFood, String species, String fruit) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(fruit + species);
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setMaxStackSize(64);
    }
}
