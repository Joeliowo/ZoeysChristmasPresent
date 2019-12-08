package com.androbean.zcp.client.render;

import com.androbean.zcp.client.render.layers.LayerCarrotPepoItem;
import com.androbean.zcp.client.render.layers.LayerPepoSkin;
import com.androbean.zcp.client.render.layers.LayerPotatoPepoItem;
import com.androbean.zcp.entity.EntityCarrotPepo;
import com.androbean.zcp.entity.EntityPotatoPepo;
import mod.akrivus.kagic.client.model.ModelPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderPotatoPepo extends RenderLivingBase<EntityPotatoPepo> {

    public RenderPotatoPepo(){
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPepo(), 0.25F);
        this.addLayer(new LayerPotatoPepoItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerPepoSkin(this));
    }

    @Override
    protected void preRenderCallback(EntityPotatoPepo entitylivingbaseIn, float partialTickTime) {
        if(entitylivingbaseIn.getAge() == 0){
            GlStateManager.scale(.6, .6, .6);
        }
        else{
            GlStateManager.scale(1, 1, 1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPotatoPepo entity) {
        return new ResourceLocation("zcp:textures/entities/potatopepo/melon.png");
    }
}
