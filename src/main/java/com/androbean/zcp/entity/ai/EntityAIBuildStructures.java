package com.androbean.zcp.entity.ai;

import com.androbean.zcp.entity.EntityPepoVillager;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIBuildStructures extends EntityAIBase {
    public final EntityPepoVillager pepo;
    public final double speed;

    public EntityAIBuildStructures(EntityPepoVillager pepo, double speed){
        this.pepo = pepo;
        this.speed = speed;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return pepo.building && pepo.hasBuildingMaterial();
    }
}
