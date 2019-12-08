package com.androbean.zcp.client.render;

import com.androbean.zcp.client.render.layers.LayerPepoSkin;
import com.androbean.zcp.client.render.layers.LayerPotatoPepoItem;
import com.androbean.zcp.client.render.layers.LayerWheatPepoItem;
import com.androbean.zcp.entity.EntityPotatoPepo;
import com.androbean.zcp.entity.EntityWheatPepo;
import mod.akrivus.kagic.client.model.ModelPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderWheatPepo extends RenderLivingBase<EntityWheatPepo> {

    public RenderWheatPepo(){
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPepo(), 0.25F);
        this.addLayer(new LayerWheatPepoItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerPepoSkin(this));
    }

    @Override
    protected void preRenderCallback(EntityWheatPepo entitylivingbaseIn, float partialTickTime) {
        if(entitylivingbaseIn.getAge() == 0){
            GlStateManager.scale(.6, .6, .6);
        }
        else{
            GlStateManager.scale(1, 1, 1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWheatPepo entity) {
        return new ResourceLocation("zcp:textures/entities/wheatpepo/melon.png");
    }
}
