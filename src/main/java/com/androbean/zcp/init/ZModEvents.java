package com.androbean.zcp.init;

import com.androbean.zcp.ZCP;
import com.androbean.zcp.entity.EntityPepoAnimal;
import com.androbean.zcp.entity.EntityPepoVillager;
import com.google.common.base.Predicate;
import mod.akrivus.kagic.entity.EntityPepo;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.heimrarnadalr.kagic.world.structure.RoseFountain;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

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
            ((EntityRoseQuartz) event.getEntity()).ROSE_RECIPES.put(Items.PUMPKIN_SEEDS, ZModItems.PUMPKIN_SEEDS);
            ((EntityRoseQuartz) event.getEntity()).ROSE_RECIPES.put(Items.POTATO, ZModItems.POTATO_SEEDS);
            ((EntityRoseQuartz) event.getEntity()).ROSE_RECIPES.put(Items.CARROT, ZModItems.CARROT_SEEDS);
            ((EntityRoseQuartz) event.getEntity()).ROSE_RECIPES.put(Items.WHEAT_SEEDS, ZModItems.WHEAT_SEEDS);
            ((EntityRoseQuartz) event.getEntity()).ROSE_RECIPES.put(Items.EGG, this.getRandomEgg());
        }
    }

    @SubscribeEvent
    public void MakeHostile(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EntityCreature){
            EntityCreature mob = (EntityCreature) event.getEntity();
            if(mob instanceof EntityWolf && !((EntityWolf) mob).isTamed() || mob instanceof EntityMob || mob instanceof EntityIronGolem){
                mob.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPepoAnimal>(mob, EntityPepoAnimal.class, true));
                mob.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPepoVillager>(mob, EntityPepoVillager.class, true));
            }
        }
    }

    public Item getRandomEgg(){
        Item item = null;
        Random random = new Random();
        int r = random.nextInt(5);
        switch(r){
            case 0:
                return ZModItems.CARROT_CHICKEN_SEEDS;
            case 1:
                return ZModItems.CHICKEN_SEEDS;
            case 2:
                return ZModItems.POTATO_CHICKEN_SEEDS;
            case 3:
                return ZModItems.WHEAT_CHICKEN_SEEDS;
            case 4:
                return ZModItems.PUMPKIN_CHICKEN_SEEDS;
        }
        return item;
    }
}
