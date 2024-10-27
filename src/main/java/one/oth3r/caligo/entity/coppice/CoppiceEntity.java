package one.oth3r.caligo.entity.coppice;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.sound.ModSounds;
import one.oth3r.caligo.tag.ModItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class CoppiceEntity extends AnimalEntity implements InventoryOwner, VariantHolder<CoppiceEntity.Variant> {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState holdAnimationState = new AnimationState();
    private int holdAnimationTimeout = 0;

    public final AnimationState eatingAnimationState = new AnimationState();


    private static final TrackedData<Boolean> EATING = DataTracker.registerData(CoppiceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PANICKING = DataTracker.registerData(CoppiceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> SOUND_COOLDOWN = DataTracker.registerData(CoppiceEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(CoppiceEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public static final float WALKING_SPEED = 0.4f;
    public static final float ADMIRE_SPEED = 0.6f;
    public static final float RUNNING_SPEED = 0.8f;


    private static final EntityDimensions BABY_BASE_DIMENSIONS = ModEntities.COPPICE
            .getDimensions()
            .withAttachments(EntityAttachments.builder().add(EntityAttachmentType.PASSENGER, 0.0F, ModEntities.COPPICE.getHeight(), -0.25F))
            .scaled(0.65F);

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

    // BRAIN

    @Override
    protected Brain.Profile<CoppiceEntity> createBrainProfile() {
        return CoppiceBrain.createBrainProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CoppiceBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<CoppiceEntity> getBrain() {
        return (Brain<CoppiceEntity>) super.getBrain();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(EATING,false);
        builder.add(PANICKING, false); // todo see if the entity render data in 1.21 fixes these issues
        builder.add(SOUND_COOLDOWN, 0);
        builder.add(VARIANT, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.setVariant(Variant.getRandom());
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    // ANIMATION THINGS
    private void setupAnimationStates() {
        // loop the idle animation
        if (this.idleAnimationTimeout <= 0) {
            // the amount of times to run the animation, at least 32 ticks long
            this.idleAnimationTimeout = (this.random.nextInt(7)+1)*32;

            // if already running, stop; else start the idle animation
            if (idleAnimationState.isRunning()) {
                this.idleAnimationState.stop();
                // extend further
                this.idleAnimationTimeout += this.random.nextBetween(40,200);
            } else {
                this.idleAnimationState.start(this.age);
            }

        } else {
            // tick the animation timeout
            --this.idleAnimationTimeout;
        }

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

    public void setPanicking(boolean b) {
        this.dataTracker.set(PANICKING, b);
    }

    public boolean isPanicking() {
        return this.dataTracker.get(PANICKING);
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

        this.getWorld().getProfiler().push("caligoActivityTick");
        CoppiceBrain.tickActivities(this);
        this.getWorld().getProfiler().pop();

        super.mobTick();
    }

    // BREEDING / BABY ----------------------

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ModItemTags.COPPICE_FOOD);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        CoppiceEntity child = ModEntities.COPPICE.create(world);
        assert child != null;

        // set the variant to either parent
        if (this.random.nextBoolean()) {
            child.setVariant(this.getVariant());
        } else child.setVariant(((CoppiceEntity)entity).getVariant());

        return child;
    }

    @Override
    public void setBaby(boolean baby) {
        this.setBreedingAge(baby ? -48000 : 0);
    }

    @Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        return isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    @Override
    public float getScaleFactor() {
        return this.isBaby() ? 0.65F : 1.0F;
    }

    // ITEMS -----------------

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

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.COPPICE_DAMAGE;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.COPPICE_DEATH;
    }

    @Override
    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.getId());
    }

    @Override
    public Variant getVariant() {
        return Variant.byId(this.dataTracker.get(VARIANT));
    }

    public enum Variant implements StringIdentifiable {
        LUSH(0, "lush"),
        MARIGOLD(1, "marigold"),
        AUTUMN(2, "autumn"),
        MOSS(3, "moss"),
        PETUNIA(4, "petunia"),
        CHERRY(5, "cherry");

        private static final IntFunction<CoppiceEntity.Variant> BY_ID = ValueLists.createIdToValueFunction(
                CoppiceEntity.Variant::getId, values(), ValueLists.OutOfBoundsHandling.ZERO
        );

        private final int id;
        private final String name;

        Variant(final int id, final String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }

        public static CoppiceEntity.Variant byId(int id) {
            return BY_ID.apply(id);
        }

        public static Variant getRandom() {
            WeightedList<Variant> variantList = new WeightedList<>();
            variantList.add(LUSH,20);
            variantList.add(MARIGOLD,9);
            variantList.add(PETUNIA,8);
            variantList.add(AUTUMN,12);
            variantList.add(MOSS, 14);
            variantList.add(CHERRY,1);

            variantList.shuffle();
            if (variantList.stream().findFirst().isPresent()) {
                return variantList.stream().findFirst().get();
            } else return LUSH;
        }
    }
}
