package com.androbean.zcp.init;

import com.androbean.zcp.ZCP;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ZCP.MODID)
public class ZRegistry {

    public ZRegistry() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new ZRegistry());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        ZModItems.registerItems(event);
        ZModBlocks.registerBlockItems(event);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){
        ZModBlocks.registerBlocks(event);
    }
}
