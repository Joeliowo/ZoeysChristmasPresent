package com.androbean.zcp.client.render;

import com.androbean.zcp.client.render.layers.LayerPepoItem;
import com.androbean.zcp.client.render.layers.LayerPepoSkin;
import com.androbean.zcp.entity.EntityMelonPepo;
import mod.akrivus.kagic.client.model.ModelPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderMelonPepo extends RenderLivingBase<EntityMelonPepo> {

    public RenderMelonPepo(){
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPepo(), 0.25F);
        this.addLayer(new LayerPepoItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerPepoSkin(this));
    }

    @Override
    protected void preRenderCallback(EntityMelonPepo entitylivingbaseIn, float partialTickTime) {
        if(entitylivingbaseIn.getAge() == 0){
            GlStateManager.scale(.6, .6, .6);
        }
        else{
            GlStateManager.scale(1, 1, 1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMelonPepo entity) {
        return new ResourceLocation("zcp:textures/entities/melonpepo/melon.png");
    }
}
