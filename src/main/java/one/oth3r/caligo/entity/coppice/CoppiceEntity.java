package one.oth3r.caligo.entity.coppice;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.entity.ai.ModSensorTypes;
import one.oth3r.caligo.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

public class CoppiceEntity extends AnimalEntity implements InventoryOwner {
    protected static final ImmutableList<SensorType<? extends Sensor<? super CoppiceEntity>>> SENSOR_TYPES;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState holdAnimationState = new AnimationState();
    private int holdAnimationTimeout = 0;

    public final AnimationState eatingAnimationState = new AnimationState();


    private static final TrackedData<Boolean> EATING = DataTracker.registerData(CoppiceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> SOUND_COOLDOWN = DataTracker.registerData(CoppiceEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public static final float WALKING_SPEED = 0.4f;
    public static final float RUNNING_SPEED = 0.8f;


    private final SimpleInventory inventory = new SimpleInventory(1);

    public CoppiceEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.hasCustomName() && this.doesNotHaveItemInHand();
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.hasCustomName();
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return SpawnReason.isTrialSpawner(spawnReason) || world.getLightLevel(LightType.SKY, pos) < 10;
    }

    public static DefaultAttributeContainer.Builder createLushAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, WALKING_SPEED);
    }

    protected Brain.Profile<CoppiceEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULE_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CoppiceBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    public Brain<CoppiceEntity> getBrain() {
        return (Brain<CoppiceEntity>) super.getBrain();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(EATING,false);
        builder.add(SOUND_COOLDOWN, 0);
    }

    // ANIMATION THINGS
    private void setupAnimationStates() {
        // loop the idle animation
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(80)+32; // at least 32 ticks long
            this.idleAnimationState.start(this.age);
        } else --this.idleAnimationTimeout;

        if (!getMainHandStack().isEmpty() && this.holdAnimationTimeout <= 0) {
            this.holdAnimationTimeout = 80; // 40 ticks long
            this.holdAnimationState.start(this.age);
        } else {
            --this.holdAnimationTimeout;
            if (getMainHandStack().isEmpty()) {
                this.holdAnimationState.stop();
            }
        }

        if (isEating()) {
            this.eatingAnimationState.stop();
            this.holdAnimationState.stop();
            this.holdAnimationTimeout = 80;

            this.setEating(false);
            this.eatingAnimationState.start(this.age);
        }

    }

    public void setEating(boolean state) {
        this.dataTracker.set(EATING,state);
    }

    public boolean isEating() {
        return this.dataTracker.get(EATING);
    }

    public void setSoundCooldown(int soundCooldown) {
        this.dataTracker.set(SOUND_COOLDOWN, soundCooldown);
    }

    public int getSoundCooldown() {
        return this.dataTracker.get(SOUND_COOLDOWN);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        if (world.isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("coppiceBrain");
        this.getBrain().tick((ServerWorld)this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        CoppiceBrain.tickActivities(this);
        super.mobTick();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
//        return stack.isIn(ModItemTags.COPPICE_FOOD);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.COPPICE.create(world);
    }

    @Override
    public void setBaby(boolean baby) {
        this.setBreedingAge(baby ? -48000 : 0);
    }

    public boolean doesNotHaveItemInHand() {
        return this.getMainHandStack().isEmpty();
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean canGather(ItemStack stack) {
        return this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) &&
                this.canPickUpLoot() && CoppiceBrain.canGather(this, stack);
    }

    @Override
    protected void loot(ItemEntity item) {
        this.triggerItemPickedUpByEntityCriteria(item);
        CoppiceBrain.loot(this, item);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean shouldDamage = super.damage(source, amount);

        if (this.getWorld().isClient) return false;

        if (shouldDamage && source.getAttacker() instanceof LivingEntity) {
            CoppiceBrain.onAttacked(this, (LivingEntity)source.getAttacker());
        }

        return shouldDamage;
    }

    @Override
    public SimpleInventory getInventory() {
        return inventory;
    }

    protected void equipToMainHand(ItemStack stack) {
        this.equipLootStack(EquipmentSlot.MAINHAND, stack);
    }

    static {
        SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_ITEMS, SensorType.HURT_BY, ModSensorTypes.COPPICE_SPECIFIC_SENSOR, ModSensorTypes.COPPICE_TEMPTATIONS);

        MEMORY_MODULE_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.IS_PANICKING,
                MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,

                MemoryModuleType.BREED_TARGET,
                MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED,

                MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.AVOID_TARGET, MemoryModuleType.NEAREST_REPELLENT,

                MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
                MemoryModuleType.ADMIRING_ITEM, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleType.ADMIRING_DISABLED,
                MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.COPPICE_DAMAGE;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.COPPICE_DEATH;
    }
}
