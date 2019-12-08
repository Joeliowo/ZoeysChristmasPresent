package com.androbean.zcp.client.render;

import com.androbean.zcp.client.render.layers.LayerCarrotPepoItem;
import com.androbean.zcp.client.render.layers.LayerMelonPepoItem;
import com.androbean.zcp.client.render.layers.LayerPepoSkin;
import com.androbean.zcp.entity.EntityCarrotPepo;
import mod.akrivus.kagic.client.model.ModelPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderCarrotPepo extends RenderLivingBase<EntityCarrotPepo> {

    public RenderCarrotPepo(){
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPepo(), 0.25F);
        this.addLayer(new LayerCarrotPepoItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerPepoSkin(this));
    }

    @Override
    protected void preRenderCallback(EntityCarrotPepo entitylivingbaseIn, float partialTickTime) {
        if(entitylivingbaseIn.getAge() == 0){
            GlStateManager.scale(.6, .6, .6);
        }
        else{
            GlStateManager.scale(1, 1, 1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCarrotPepo entity) {
        return new ResourceLocation("zcp:textures/entities/carrotpepo/melon.png");
    }
}
