package com.androbean.zcp.client.render;

import com.androbean.zcp.client.render.layers.LayerPepoAnimalSkin;
import com.androbean.zcp.client.render.layers.LayerPepoItem;
import com.androbean.zcp.entity.EntityMelonChicken;
import com.androbean.zcp.entity.EntityMelonPepo;
import mod.akrivus.kagic.client.model.ModelPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderMelonChicken extends RenderLivingBase<EntityMelonChicken> {

    public RenderMelonChicken(){
        super(Minecraft.getMinecraft().getRenderManager(), new ModelChicken(), 0.25F);
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerPepoAnimalSkin(this));
    }

    @Override
    protected void preRenderCallback(EntityMelonChicken entitylivingbaseIn, float partialTickTime) {
        if(entitylivingbaseIn.getAge() == 0){
            GlStateManager.scale(.5, .5, .5);
        }
        else {
            GlStateManager.scale(1, 1, 1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMelonChicken entity) {
        return new ResourceLocation("zcp:textures/entities/chickenpepo/chicken.png");
    }
}
