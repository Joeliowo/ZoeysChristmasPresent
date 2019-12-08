package com.androbean.zcp.entity;

import com.androbean.zcp.entity.ai.EntityAIBuildStructures;
import com.androbean.zcp.entity.ai.EntityAIHarvestAnimals;
import com.androbean.zcp.init.ZModItems;
import com.androbean.zcp.structures.Structure;
import com.androbean.zcp.structures.Village;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import io.netty.buffer.ByteBuf;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityPepoVillager extends EntityCreature implements IInventoryChangedListener, INpc, IEntityOwnable, IEntityAdditionalSpawnData {
    protected static final DataParameter<Boolean> HUNGRY = EntityDataManager.<Boolean>createKey(EntityPepoVillager.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> CLAIMED = EntityDataManager.<Boolean>createKey(EntityPepoVillager.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> CLAIMABLE = EntityDataManager.<Boolean>createKey(EntityPepoVillager.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> HUNGER = EntityDataManager.<Integer>createKey(EntityPepoVillager.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.<Integer>createKey(EntityPepoVillager.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> MAX_AGE = EntityDataManager.<Integer>createKey(EntityPepoVillager.class, DataSerializers.VARINT);
    protected static final DataParameter<String> JOB = EntityDataManager.<String>createKey(EntityPepoVillager.class, DataSerializers.STRING);
    protected static final DataParameter<Optional<UUID>> OWNER_ID = EntityDataManager.<Optional<UUID>>createKey(EntityPepoVillager.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    public final EntityAITarget attackMobs = new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class,
            10, true, false, new Predicate<EntityLivingBase>() {
        public boolean apply(EntityLivingBase input) {
            return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
        }
    });
    public final EntityAIHarvestAnimals harvest = new EntityAIHarvestAnimals(this, 0.3D);
    public final EntityAIBuildStructures buildStructures = new EntityAIBuildStructures(this, 1.0D);
    public static final int ROTTING_SKIN_COLOR = 0xE0C0A4;
    public Village village = null;
    public Structure build;
    private UUID leader = null;
    public Item dropItem;
    public Item eatItem;
    public Item seedItem;
    public Item fruitSeed;
    public Block fruitStem;
    public InventoryBasic storage;
    public InvWrapper storageHandler;
    public int ageTicks = 0;
    public int buildingBlock = 0;
    public boolean building = false;

    public EntityPepoVillager(World worldIn) {
        super(worldIn);
        this.seePastDoors();
        this.stepHeight = 0.6F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictOpenDoor(this));
        this.dataManager.register(HUNGRY, false);
        this.dataManager.register(CLAIMED, false);
        this.dataManager.register(CLAIMABLE, true);
        this.dataManager.register(HUNGER, 20);
        this.dataManager.register(AGE, 0);
        this.dataManager.register(MAX_AGE, 3);
        this.dataManager.register(OWNER_ID, Optional.fromNullable(this.getUniqueID()));
        this.dataManager.register(JOB, "baby");
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public boolean canDespawn() {
        return false;
    }

    public void initStorage() {
        InventoryBasic storage = this.storage;
        this.storage = new InventoryBasic("storage", false, 36);
        if (storage != null) {
            storage.removeInventoryChangeListener(this);
            for (int i = 0; i < this.storage.getSizeInventory(); ++i) {
                ItemStack itemstack = storage.getStackInSlot(i);
                this.storage.setInventorySlotContents(i, itemstack.copy());
            }
        }
        this.storage.addInventoryChangeListener(this);
        this.storageHandler = new InvWrapper(this.storage);
        this.setCanPickUpLoot(true);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.storage.getSizeInventory(); ++i) {
            ItemStack itemstack = this.storage.getStackInSlot(i);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("slot", (byte)i);
            itemstack.writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
        }
        compound.setTag("items", nbttaglist);
        compound.setBoolean("hungry", this.getIsHungry());
        compound.setBoolean("claimed", this.getIsClaimable());
        compound.setBoolean("claimable", this.getIsClaimable());
        compound.setBoolean("building", this.building);
        compound.setInteger("hunger", this.getHunger());
        compound.setInteger("ageTicks", this.ageTicks);
        compound.setInteger("age", this.getAge());
        compound.setInteger("buildingBlock", this.buildingBlock);
        compound.setString("job", this.getJob());
        if (this.getOwnerId() == null) {
            compound.setString("ownerId", "");
        }
        else {
            compound.setString("ownerId", this.getOwnerId().toString());
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("items", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("slot") & 255;
            if (j >= 0 && j < this.storage.getSizeInventory()) {
                this.storage.setInventorySlotContents(j, new ItemStack(nbttagcompound));
            }
        }
        this.setIsHungry(compound.getBoolean("hungry"));
        this.setIsClaimed(compound.getBoolean("claimed"));
        this.setIsClaimable(compound.getBoolean("claimable"));
        this.setIsClaimable(compound.getBoolean("claimable"));
        this.setHunger(compound.getInteger("hunger"));
        this.buildingBlock = compound.getInteger("buildingBlock");
        this.ageTicks = compound.getInteger("ageTicks");
        this.setAge(compound.getInteger("age"));
        this.setJob(compound.getString("job"));
        if(compound.getString("job") == "defender"){
            this.targetTasks.addTask(9, this.attackMobs);
        }
        String ownerId;
        if (compound.hasKey("ownerId", 8)) {
            ownerId = compound.getString("ownerId");
        }
        else {
            String tempId = compound.getString("ownerId");
            ownerId = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), tempId);
        }
        if (!ownerId.isEmpty()) {
            this.setOwnerId(Optional.fromNullable(UUID.fromString(ownerId)).orNull());
        }
    }

    @Override
    protected void updateEquipmentIfNeeded(EntityItem itementity) {
        ItemStack itemstack = itementity.getItem();
        if (this.canPickupItem(itemstack.getItem())) {
            ItemStack itemstack1 = this.storage.addItem(itemstack);
            if (itemstack1.isEmpty()) {
                itementity.setDead();
            }
            else {
                itemstack.setCount(itemstack1.getCount());
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand){
        ItemStack stack = player.getHeldItemMainhand();
        if (this.isOwner(player)) {
            if(this.getAge() > 0) {
                if (stack.getItem() instanceof ItemSword) {
                    this.tasks.removeTask(this.harvest);
                    this.tasks.removeTask(this.buildStructures);
                    boolean toolChanged = true;
                    if (!this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isItemEqualIgnoreDurability(stack)) {
                        this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), 0.0F);
                        this.setHeldItem(EnumHand.MAIN_HAND, player.getHeldItemMainhand());
                    }
                    else{
                        toolChanged = false;
                    }
                    if(toolChanged){
                        ItemStack heldItem = stack.copy();
                        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }
                    }
                    this.targetTasks.addTask(9, this.attackMobs);
                    this.setJob("defender");
                    return true;
                }
                else if (stack.getItem() instanceof ItemHoe) {
                    this.targetTasks.removeTask(this.attackMobs);
                    this.targetTasks.removeTask(this.buildStructures);
                    boolean toolChanged = true;
                    if (!this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isItemEqualIgnoreDurability(stack)) {
                        this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), 0.0F);
                        this.setHeldItem(EnumHand.MAIN_HAND, player.getHeldItemMainhand());
                    }
                    else{
                        toolChanged = false;
                    }
                    if(toolChanged){
                        ItemStack heldItem = stack.copy();
                        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }
                    }
                    this.setCanPickUpLoot(true);
                    this.setJob("farmer");
                    return true;
                }
                /*else if (stack.getItem() == Items.STICK) {
                    this.targetTasks.removeTask(this.attackMobs);
                    this.targetTasks.removeTask(this.harvest);
                    boolean toolChanged = true;
                    if (!this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isItemEqualIgnoreDurability(stack)) {
                        this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), 0.0F);
                        this.setHeldItem(EnumHand.MAIN_HAND, player.getHeldItemMainhand());
                    }
                    else{
                        toolChanged = false;
                    }
                    if(toolChanged){
                        ItemStack heldItem = stack.copy();
                        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }
                    }
                    this.setCanPickUpLoot(true);
                    this.tasks.addTask(9, this.buildStructures);
                    this.setJob("builder");
                    return true;
                }*/
                else if(this.isFarmer() || this.isBuilder()){
                    this.openInventory(player);
                    return true;
                }
            }
        }
        if (!this.isOwner(player)) {
            if (this.getIsClaimable()) {
                if (player.getHeldItemMainhand().getItem() == ZModItems.ANIMAL_MELON) {
                    this.Claim(player.getUniqueID());
                    return true;
                }
            }
        }
        return super.processInteract(player, hand);
    }

    public boolean canPickupItem(Item itemIn) {
        if (this.isFarmer()) {
            return itemIn == ZModItems.CHICKEN_SEEDS;
        }
        return false;
    }

    public boolean isFarmer() {
        return this.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemHoe;
    }

    public boolean isFighter(){
        return this.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword;
    }

    public boolean isBuilder(){
        return this.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.STICK;
    }

    public boolean hasBuildingMaterial(){
        boolean has = false;
        if(this.building && this.build != null) {
            for (int i = 0; i < this.storage.getSizeInventory(); i++) {
                if (this.isBuildingMaterial(this.storage.getStackInSlot(i))){
                    return true;
                }
            }
        }
        else{
            return false;
        }
        return has;
    }

    public boolean isBuildingMaterial(ItemStack stack){
        Item item = stack.getItem();
        Map<IBlockState, BlockPos> block;
        for(BlockPos i : this.build.structureData.getStructureBlocks().keySet()){
            if(stack == new ItemStack(this.build.structureData.structureBlocks.get(i).getBlock())){
                return true;
            }
        }
        for(int i = 0; i < this.storage.getSizeInventory(); i++){
            if(this.storage.getStackInSlot(i) == new ItemStack(Blocks.DIRT)){
                return true;
            }
        }
        return false;
    }

    public void openInventory(EntityPlayer player){
        this.storage.setCustomName(this.getName());
        player.displayGUIChest(this.storage);
    }

    public void Claim(UUID player){
        this.setOwnerId(player);
        this.setIsClaimed(true);
        this.setIsClaimable(false);
        this.playTameEffect();
    }

    public boolean isOwner(EntityLivingBase entity){
        if(entity != null){
            if(entity instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)entity;
                if(player.getUniqueID().equals(this.getOwnerId())){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    public int checkForFood(){
        boolean isFood = false;
        for(int i = 0; i < this.storage.getSizeInventory(); i++){
            if(this.storage.getStackInSlot(i).getItem() == this.eatItem){
                isFood = true;
                return i;
            }
        }
        if(!isFood){
            this.setIsHungry(true);
        }
        return 420;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.getTrueSource() instanceof EntityLivingBase){
            if(this.isOwner((EntityLivingBase) source.getTrueSource())) {
                if (this.rand.nextInt(100) != 1) {
                    return super.attackEntityFrom(source, amount);
                }
                else{
                    this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
                    return super.attackEntityFrom(source, amount);
                }
            }
            else{
                this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
                return super.attackEntityFrom(source, amount);
            }
        }
        return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        float amount = (float)(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        this.swingArm(EnumHand.MAIN_HAND);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), amount);
    }

    @Override
    public void onLivingUpdate(){
        if(this.ageTicks >= this.growthStage()){
            this.setAge(this.getAge() + 1);
            this.ageTicks = 0;
            this.onAgeUp();
            if(this.getAge() >= this.getMaxAge()){
                this.setHealth(0);
            }
        }
        this.ageTicks++;
        super.onLivingUpdate();
    }

    public void onAgeUp(){

    }

    public int growthStage(){
        switch (this.getAge()){
            case 0:
                return 12000; // 12000
            case 1:
                return 295200; // 295200
            case 2:
                return 24000; // 24000
        }
        return 9000;
    }

    public void seePastDoors() {
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround) this.getNavigator()).setEnterDoors(true);
    }

    public void onDeath(DamageSource cause) {
        ItemStack drop = new ItemStack(this.dropItem);
        if (!this.world.isRemote) {
            if(this.getAge() != 3) {
                this.entityDropItem(drop, 0.0F);
                this.entityDropItem(new ItemStack(this.seedItem, this.rand.nextInt(3)), 0.0F);
            }
            for(int i = 0; i < this.storage.getSizeInventory(); i++){
                this.entityDropItem(this.storage.getStackInSlot(i), 0.0F);
            }
        }
        super.onDeath(cause);
    }

    public void playTameEffect() {
        EnumParticleTypes enumparticletypes = EnumParticleTypes.VILLAGER_HAPPY;
        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(enumparticletypes, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
        }
    }

    public int getSkinColor(){
        if(this.getAge() == this.getMaxAge() - 1){
            return ROTTING_SKIN_COLOR;
        }
        else{
            return 0xFFFFFF;
        }
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.PEPO_LIVING;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.PEPO_LIVING;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.PEPO_LIVING;
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {

    }

    public boolean getIsHungry(){
        return this.dataManager.get(HUNGRY);
    }

    public void setIsHungry(boolean value){
        this.dataManager.set(HUNGRY, value);
    }

    public boolean getIsClaimed(){
        return this.dataManager.get(CLAIMED);
    }

    public void setIsClaimed(boolean value){
        this.dataManager.set(CLAIMED, value);
    }

    public boolean getIsClaimable(){
        return this.dataManager.get(CLAIMABLE);
    }

    public void setIsClaimable(boolean value){
        this.dataManager.set(CLAIMABLE, value);
    }

    public int getHunger(){
        return this.dataManager.get(HUNGER);
    }

    public void setHunger(int value){
        this.dataManager.set(HUNGER, value);
    }

    public int getAge(){
        return this.dataManager.get(AGE);
    }

    public void setAge(int value){
        this.dataManager.set(AGE, value);
    }

    public int getMaxAge(){
        return this.dataManager.get(MAX_AGE);
    }

    public void setMaxAge(int value){
        this.dataManager.set(MAX_AGE, value);
    }

    public String getJob(){
        return this.dataManager.get(JOB);
    }

    public void setJob(String value){
        this.dataManager.set(JOB, value);
        this.dataManager.set(JOB, value);
        this.dataManager.set(JOB, value);
        this.dataManager.set(JOB, value);
    }

    @Nullable
    @Override
    public UUID getOwnerId() {
        return (UUID) ((Optional<UUID>) this.dataManager.get(OWNER_ID)).orNull();
    }

    @Nullable
    @Override
    public Entity getOwner() {
        EntityPlayer p = null;
        for(EntityPlayer player :this.world.playerEntities){
            if(player.getUniqueID() == this.getOwnerId()){
                p = player;
            }
        }
        return p;
    }

    public void setOwnerId(UUID ownerId) {
        this.dataManager.set(OWNER_ID, Optional.fromNullable(ownerId));
    }

    public void setOwnerId(String ownerId) {
        this.dataManager.set(OWNER_ID, Optional.fromNullable(UUID.fromString(ownerId)));
    }

    public UUID getLeader() {
        return this.leader;
    }

    public void setLeader(UUID newLeader) {
        this.leader = newLeader;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(this.width);
        buffer.writeFloat(this.height);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.setSize(buffer.readFloat(), buffer.readFloat());
    }
}
