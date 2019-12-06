package com.androbean.zcp.client.render.layers;

import com.androbean.zcp.entity.EntityPepoVillager;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerPepoSkin implements LayerRenderer<EntityPepoVillager> {
    private final RenderLivingBase<?> gemRenderer;
    private final ModelBase gemModel;
    private float offset;
    private String name;

    public LayerPepoSkin(RenderLivingBase<?> gemRenderer) {
        this(gemRenderer, 0F);
    }

    public LayerPepoSkin(RenderLivingBase<?> gemRenderer, float offset) {
        this(gemRenderer, offset, null);
    }

    public LayerPepoSkin(RenderLivingBase<?> gemRenderer, float offset, String name) {
        this.gemRenderer = gemRenderer;
        this.gemModel = gemRenderer.getMainModel();
        this.offset = offset;
        this.name = name;
    }

    @Override
    public void doRenderLayer(EntityPepoVillager pepo, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.gemRenderer.bindTexture(this.getTexture(pepo));
        int skin = pepo.getSkinColor();
        float r = (float) ((skin & 16711680) >> 16) / 255f;
        float g = (float) ((skin & 65280) >> 8) / 255f;
        float b = (float) ((skin & 255) >> 0) / 255f;
        //KAGIC.instance.chatInfoMessage("Skin color is " + r + " , " + g + " , " + b);
        GlStateManager.color(r + this.offset, g + this.offset, b + this.offset, 1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(pepo, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
    }

    public ResourceLocation getTexture(EntityPepoVillager gem) {
        ResourceLocation loc = EntityList.getKey(gem);
        return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/skin.png");
    }

    public String getName(EntityPepoVillager gem) {
        if (this.name != null) {
            return this.name;
        } else {
            ResourceLocation loc = EntityList.getKey(gem);
            if (loc.getResourceDomain().equals("zcp")) {
                return loc.getResourcePath().replaceFirst("zcp.", "");
            }
            else {
                return loc.getResourcePath();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }

}
