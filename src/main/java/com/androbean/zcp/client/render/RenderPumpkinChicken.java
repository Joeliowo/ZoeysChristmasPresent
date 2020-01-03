package com.androbean.zcp.client.render;

import com.androbean.zcp.client.render.layers.LayerPepoAnimalSkin;
import com.androbean.zcp.client.render.models.ModelFruitChicken;
import com.androbean.zcp.entity.EntityMelonChicken;
import com.androbean.zcp.entity.EntityPumpkinChicken;
import com.androbean.zcp.entity.EntityPumpkinPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderPumpkinChicken extends RenderLivingBase<EntityPumpkinChicken> {

    public RenderPumpkinChicken(){
        super(Minecraft.getMinecraft().getRenderManager(), new ModelFruitChicken(), 0.25F);
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerPepoAnimalSkin(this));
    }

    @Override
    protected void preRenderCallback(EntityPumpkinChicken entitylivingbaseIn, float partialTickTime) {
        if(entitylivingbaseIn.getAge() == 0){
            GlStateManager.scale(.5, .5, .5);
        }
        else {
            GlStateManager.scale(1, 1, 1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPumpkinChicken entity) {
        return new ResourceLocation("zcp:textures/entities/pumpkinchickenpepo/chicken.png");
    }
}
