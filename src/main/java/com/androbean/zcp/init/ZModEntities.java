package com.androbean.zcp.init;

import com.androbean.zcp.ZCP;
import com.androbean.zcp.entity.EntityMelonChicken;
import com.androbean.zcp.entity.EntityMelonPepo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ZModEntities {
    private static int currentID = 0;

    public static void register(){
        registerMob("melonpepo", EntityMelonPepo.class, 0x51D4A4, 0xF5FFCC);
        registerMob("chickenpepo", EntityMelonChicken.class, 0x51D4A4, 0xF5FFCC);
    }

    @SuppressWarnings({ "unchecked" })
    public static <T extends Entity> void registerMob(String name, Class<T> entity, int back, int fore) {
        EntityRegistry.registerModEntity(new ResourceLocation("zcp:" + name), entity, name, currentID, ZCP.instance, 256, 1, true, back, fore);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            try {
                Class<Render<? extends T>> render = (Class<Render<? extends T>>) ZCP.class.getClassLoader().loadClass("com/androbean/zcp/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
                RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ++currentID;
    }
}
