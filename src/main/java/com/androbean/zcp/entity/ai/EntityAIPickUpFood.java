package com.androbean.zcp.entity.ai;

import com.androbean.zcp.entity.EntityPepoVillager;
import com.androbean.zcp.items.ItemFruit;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;

import java.util.List;

public class EntityAIPickUpFood extends EntityAIBase {
    private final EntityPepoVillager gem;
    private final double movementSpeed;
    private EntityItem item;

    public EntityAIPickUpFood(EntityPepoVillager entityIn, double movementSpeedIn) {
        this.gem = entityIn;
        this.movementSpeed = movementSpeedIn;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        List<EntityItem> list = this.gem.world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, this.gem.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityItem item : list) {
            double newDistance = this.gem.getDistanceSq(item);
            if (newDistance <= maxDistance && this.gem.canEntityBeSeen(item) && !item.isDead && item.getItem().getItem() instanceof ItemFruit) {
                maxDistance = newDistance;
                this.item = item;
            }
        }
        return this.item != null && !this.item.isDead && this.gem.canPickUpLoot();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.item != null
                && !this.item.isDead
                && this.gem.canEntityBeSeen(this.item)
                && !this.gem.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.gem.getLookHelper().setLookPositionWithEntity(this.item, 30.0F, 30.0F);
    }

    @Override
    public void resetTask() {
        this.gem.getNavigator().clearPath();
        this.item = null;
    }

    @Override
    public void updateTask() {
        if (this.gem.getDistanceSq(this.item) > 1F) {
            this.gem.getNavigator().tryMoveToEntityLiving(this.item, this.movementSpeed);
        }
    }
}
