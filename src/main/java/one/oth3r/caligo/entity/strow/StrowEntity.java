package one.oth3r.caligo.entity.strow;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class StrowEntity extends HostileEntity implements Angerable {
    public final AnimationState activeAnimationState = new AnimationState();
    private int activeAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(StrowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ACTIVE = DataTracker.registerData(StrowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(StrowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int angerTime;
    private int cawTime;
    @Nullable
    private UUID angryAt;
    private LivingEntity target;
    public StrowEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    // INIT THINGS
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new ChasePlayerGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true, false));
    }

    public static DefaultAttributeContainer.Builder createBirdAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, .3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,.7);
    }

    public static boolean canSpawn(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random) && world.getLightLevel(LightType.SKY, pos) == 0 &&
                pos.getY() > 0;
    }


    // ANIMATION THINGS
    private void setupAnimationStates() {
        // loop the active animation
        if (this.isAngry() && this.activeAnimationTimeout <= 0) {
            this.activeAnimationTimeout = 40;
            this.activeAnimationState.start(this.age);
        } else --this.activeAnimationTimeout;
        if (!this.isAngry()) this.activeAnimationState.stop();
        // if attacking is true play the animation and set it back to false
        if (this.isAttacking()) {
            this.attackAnimationState.start(this.age);
            this.setAttacking(false);
        }
    }
    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }


    // TICK STUFF
    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        if (world.isClient()) {
            setupAnimationStates();
        }
        if (this.isAngry()) {
            // every 4 second loop
            if (world.getTime() % 80L == 0L) {
                this.playSound(getActiveSound(),.25f, (float) random.nextBetween(98, 102) / 100);
            }
        }
        if (!this.isAngry() && this.cawTime > 0) this.cawTime--;
    }
    @Override
    protected void mobTick() {
        ServerWorld serverWorld = (ServerWorld)this.getWorld();
        if (this.age % 2 == 0) {
            // get all players in a 25 block radius and check if they are staring
            List<ServerPlayerEntity> list = serverWorld.getPlayers(player ->
                    player.interactionManager.isSurvivalLike() && !this.isTeammate(player) && this.getPos().isInRange(player.getPos(), 25));
            for (PlayerEntity player : list) petrifyStaring(serverWorld,this, player);
        }
    }

    /**
     * the custom player damage system, only allow pickaxes
     */
    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source) || source.isIn(DamageTypeTags.IS_PROJECTILE)) {
            return false;
        }
        if (isFullDamage(source)) {
            Entity attacker = source.getAttacker();
            if (attacker instanceof PlayerEntity player) {
                this.takeKnockback(1.8F, player.getX() - this.getX(), player.getZ() - this.getZ());
            }
            return super.damage(source,amount);
        }
        return super.damage(source, (float) (amount*.01));
    }

    /**
     * checks if the DamageSource will fully damage the Strow or not
     * @param source the damage source to check
     * @return if full or not
     */
    private boolean isFullDamage(DamageSource source) {
        Entity attacker = source.getAttacker();
        if (attacker instanceof PlayerEntity player) {
            ItemStack heldItem = player.getMainHandStack();
            // full damage if pickaxe, else not
            return heldItem.getItem() instanceof PickaxeItem;
        }
        return false;
    }

    public void caw(ServerWorld world, Vec3d pos, @Nullable Entity entity, int range) {
        if (this.cawTime==0) {
            List<ServerPlayerEntity> list = world.getPlayers(player -> !(!player.interactionManager.isSurvivalLike() || entity != null && entity.isTeammate(player) || !pos.isInRange(player.getPos(), range)));
            if (!list.isEmpty()) {
                this.playSound(getCawSound(),.6f, this.getSoundPitch());
                this.cawTime = 10000;
            }
        }
    }

    /**
     * checks if the target is staring and petrifies accordingly
     */
    public void petrifyStaring(ServerWorld world, StrowEntity entity, PlayerEntity target) {
        // if not angry, back
        if (!this.isAngry()) return;

        if (isPlayerStaring(target)) {
            int duration = 300;
            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(ModEffects.PETRIFIED, duration, 0, false, false);
            StatusEffect statusEffect = statusEffectInstance.getEffectType();

            // make sure the effect doesn't override a stronger effect
            boolean canApply = !target.hasStatusEffect(statusEffect)
                    || target.getStatusEffect(statusEffect).getAmplifier() <= statusEffectInstance.getAmplifier();

            if (canApply) target.addStatusEffect(statusEffectInstance);

            caw(world,entity.getPos(),this,25);
        }
    }

    /**
     * @return true if looking at eyes
     */
    protected boolean isPlayerStaring(PlayerEntity player) {
        Vec3d vec3d = player.getRotationVec(1.0F).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double distance = vec3d2.length();
        vec3d2 = vec3d2.normalize();
        double angle = vec3d.dotProduct(vec3d2);
        return angle > 1.0 - 0.025 / distance && player.canSee(this);
    }

    /**
     * @return true if looking in general location
     */
    boolean isPlayerGlancing(PlayerEntity player) {
        // Get a vector representing the player's facing direction
        Vec3d vec3d = player.getRotationVec(1.0f).normalize();
        // Calculate the vector from the player to the entity, adjusting for eye level
        Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        // Calculate the distance between the player and the entity
        double distance = vec3d2.length();
        // calculate the cosine of the angle between the player's facing direction and the vector to the entity
        double angle = vec3d.dotProduct(vec3d2.normalize());
        // determine if the entity is within the player's field of vision based on the angle and distance
        // the threshold decreases with distance
        return angle > 0.2 - 0.025 / distance && player.canSee(this);
    }

    public boolean isAngry() {
        return this.dataTracker.get(ANGRY);
    }
    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            this.dataTracker.set(ANGRY, false);
            angryAt = null;
        } else {
            this.dataTracker.set(ANGRY, true);
            setAngryAt(target.getUuid());
        }
        this.target = target;
    }
    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.target;
    }
    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGRY, false);
        this.dataTracker.startTracking(ACTIVE, false);
        this.dataTracker.startTracking(ATTACKING, false);
    }
    public boolean isActive() {
        return this.dataTracker.get(ACTIVE);
    }
    public void setActive(boolean b) {
        this.dataTracker.set(ACTIVE, b);
    }
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }
    public void setAttacking(boolean b) {
        this.dataTracker.set(ATTACKING,b);
    }

    // SOUNDS
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (isFullDamage(source)) return ModSounds.STROW_DAMAGE;
        return SoundEvents.BLOCK_STONE_PLACE;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.STROW_DEATH;
    }
    protected SoundEvent getCawSound() {
        return ModSounds.STROW_CAW;
    }
    protected SoundEvent getActiveSound() {
        return ModSounds.STROW_ACTIVE;
    }

    /**
     * player chaser when not looking
     */
    static class ChasePlayerGoal extends Goal {
        private final StrowEntity bird;
        @Nullable
        private LivingEntity target;
        private int ticksToNextAttack;

        public ChasePlayerGoal(StrowEntity bird) {
            this.bird = bird;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE, Control.LOOK));
        }
        @Override
        public boolean canStart() {
            this.target = this.bird.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            }
            double d = this.target.squaredDistanceTo(this.bird);
            // if farther than 256, cant start
            if (d > 256.0) return false;
            // if not angry at the target, nope
            if (!(this.bird.isAngry() && this.bird.angryAt.equals(this.target.getUuid()))) return false;

            return !this.bird.isPlayerGlancing((PlayerEntity)this.target);
        }
        @Override
        public void start() {
            this.bird.getNavigation().stop();
            this.bird.setActive(true);
            this.ticksToNextAttack = 0;
        }
        @Override
        public boolean shouldContinue() {
            return !this.bird.isPlayerGlancing((PlayerEntity)this.target) && this.bird.isAngry();
        }
        @Override
        public void stop() {
            super.stop();
            this.bird.getNavigation().stop();
            this.bird.setActive(false);
            this.bird.setAttacking(false);
        }
        @Override
        public void tick() {
            this.bird.getNavigation().startMovingAlong(
                    this.bird.getNavigation().findPathTo(this.target.getX(),this.target.getY(),this.target.getZ(),0),1);
            attack(this.target);
        }
        private void attack(LivingEntity pEnemy) {
            if (this.ticksToNextAttack>0) --this.ticksToNextAttack;
            if (isInDistance(pEnemy)) {
                if (this.ticksToNextAttack == 0) {
                    this.ticksToNextAttack = 6;
                    this.bird.getLookControl().lookAt(pEnemy);
                    this.bird.setAttacking(true);
                    this.bird.tryAttack(pEnemy);
                } else this.bird.setAttacking(false);
                // set attack to false to update the animation (it doesnt work if u get rid of it)
            }
        }
        private boolean isInDistance(LivingEntity pEnemy) {
            return this.bird.distanceTo(pEnemy) <= 1f;
        }
    }
}
