package com.androbean.zcp.init;

import com.androbean.zcp.ZCP;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.heimrarnadalr.kagic.world.structure.RoseFountain;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ZCP.MODID)
public class ZModEvents {

    public ZModEvents() {

    }

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new ZModEvents());
    }

    @SubscribeEvent
    public void RoseRecipes(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EntityRoseQuartz){
            ((EntityRoseQuartz) event.getEntity()).ROSE_RECIPES.put(Items.MELON_SEEDS, ZModItems.MELON_SEEDS);
            ((EntityRoseQuartz) event.getEntity()).ROSE_RECIPES.put(Items.EGG, ZModItems.CHICKEN_SEEDS);
        }
    }
}
