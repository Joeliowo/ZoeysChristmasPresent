package com.androbean.zcp.client.render.layers;

import com.androbean.zcp.entity.EntityPumpkinPepo;
import com.androbean.zcp.entity.EntityWheatPepo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPumpkinPepoItem implements LayerRenderer<EntityPumpkinPepo> {
    protected final RenderLivingBase<EntityPumpkinPepo> livingEntityRenderer;
    public LayerPumpkinPepoItem(RenderLivingBase<EntityPumpkinPepo> livingEntityRendererIn) {
        this.livingEntityRenderer = livingEntityRendererIn;
    }
    public void doRenderLayer(EntityPumpkinPepo entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.LEFT;
        ItemStack mainHand = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        ItemStack offHand = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
        if (!mainHand.isEmpty() || !offHand.isEmpty()) {
            GlStateManager.pushMatrix();
            this.renderHeldItem(entitylivingbaseIn, mainHand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(entitylivingbaseIn, offHand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }
    private void renderHeldItem(EntityPumpkinPepo entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            this.setSide(handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16F, 0.125F, -0.5F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
            GlStateManager.popMatrix();
        }
    }
    protected void setSide(EnumHandSide side) {
        ((ModelBiped) this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, side);
    }
    public boolean shouldCombineTextures() {
        return false;
    }
}