package com.androbean.zcp.client.render;

import com.androbean.zcp.client.render.layers.LayerPepoAnimalSkin;
import com.androbean.zcp.entity.EntityPotatoChicken;
import com.androbean.zcp.entity.EntityPumpkinPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderPotatoChicken extends RenderLivingBase<EntityPotatoChicken> {

    public RenderPotatoChicken(){
        super(Minecraft.getMinecraft().getRenderManager(), new ModelChicken(), 0.25F);
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerPepoAnimalSkin(this));
    }

    @Override
    protected void preRenderCallback(EntityPotatoChicken entitylivingbaseIn, float partialTickTime) {
        if(entitylivingbaseIn.getAge() == 0){
            GlStateManager.scale(.5, .5, .5);
        }
        else {
            GlStateManager.scale(1, 1, 1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPotatoChicken entity) {
        return new ResourceLocation("zcp:textures/entities/potatochickenpepo/chicken.png");
    }
}
