package one.oth3r.caligo.entity.strow.deep;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.entity.strow.StrowEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DeepStrowEntity extends StrowEntity {
    public DeepStrowEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createDeepStrowAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 8)
                .add(EntityAttributes.MOVEMENT_SPEED, .35)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.ATTACK_SPEED, 4)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE,.7);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1,new AttackGoal(this));
        this.goalSelector.add(2,new LookAtEntityGoal(this,PlayerEntity.class,20,.8f));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true, false));
    }

    public static boolean canSpawn(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random) && world.getLightLevel(LightType.SKY, pos) == 0 &&
                pos.getY() <= 0;
    }

    @Override
    protected void mobTick(ServerWorld serverWorld) {
        super.mobTick(serverWorld);

        if (this.isActive()) {
            caw(serverWorld,this.getPos(),this,25);
        }
    }

    @Override
    public void petrifyStaring(ServerWorld world, StrowEntity entity, PlayerEntity target) {
        // if not angry, back
        if (!this.isAngry()) return;

        if (isPlayerStaring(target)) {
            int duration = 150;

            RegistryEntry<StatusEffect> petrified = ModEffects.getEffect(ModEffects.PETRIFIED);

            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(petrified, duration, 1, false, false);

            // only apply the effect every 20 ticks after applying or play doesn't have effect
            boolean canApply = !target.hasStatusEffect(petrified) || target.getStatusEffect(petrified).getAmplifier() < statusEffectInstance.getAmplifier() ||
                    target.getStatusEffect(petrified).isDurationBelow(duration - 20);

            if (canApply) target.addStatusEffect(statusEffectInstance);
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        // if the attacker isnt a living entity, dont get angry
        if (!(source.getAttacker() instanceof LivingEntity)) return super.damage(world,source,amount);

        LivingEntity entity = (LivingEntity) source.getAttacker();

        // if player is in creative or spectator dont get mad at them
        if (entity instanceof PlayerEntity player) {
            if (player.isCreative() || player.isSpectator()) return super.damage(world,source,amount);
        }

        this.setTarget(entity);
        this.setAngryAt(entity.getUuid());
        this.setActive(true);

        return super.damage(world, source, amount);
    }

    static class AttackGoal extends Goal {
        private final DeepStrowEntity deepStrow;
        @Nullable
        private LivingEntity target;
        private int ticksToNextAttack;

        public AttackGoal(DeepStrowEntity deepStrow) {
            this.deepStrow = deepStrow;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            this.target = this.deepStrow.getTarget();
            // if target is null cant start
            if (this.target == null) {
                return false;
            }
            double d = this.target.squaredDistanceTo(this.deepStrow);
            // if farther than 256, cant start
            if (d > 256.0) return false;
            // if not angry at the target, nope
            if (!(this.deepStrow.isAngry() && this.deepStrow.getAngryAt().equals(this.target.getUuid()))) return false;

            return deepStrow.isActive() || d < 3;
        }


        @Override
        public void start() {
            this.deepStrow.getNavigation().stop();
            this.deepStrow.setActive(true);
            this.ticksToNextAttack = 0;
        }

        @Override
        public boolean shouldContinue() {
            return this.deepStrow.isAngry() && this.deepStrow.getAngryAt().equals(this.target.getUuid());
        }

        @Override
        public void stop() {
            super.stop();
            this.deepStrow.getNavigation().stop();
            this.deepStrow.setActive(false);
            this.deepStrow.setAttacking(false);
        }

        @Override
        public void tick() {
            this.deepStrow.getNavigation().startMovingAlong(
                    this.deepStrow.getNavigation().findPathTo(this.target.getX(),this.target.getY(),this.target.getZ(),0),1);
            attack(this.target);
        }

        private void attack(LivingEntity pEnemy) {
            // count down the ticks to next attack if needed
            if (this.ticksToNextAttack > 0) --this.ticksToNextAttack;
            // if in attacking distance
            if (isInDistance(pEnemy)) {
                // if can attack
                if (this.ticksToNextAttack == 0) {
                    this.ticksToNextAttack = 6;
                    this.deepStrow.getLookControl().lookAt(pEnemy);
                    this.deepStrow.setAttacking(true);
                    this.deepStrow.tryAttack(getServerWorld(this.deepStrow), pEnemy);
                }
                // if cant attack turn off attacking animation
                else this.deepStrow.setAttacking(false);
                // set attack to false to update the animation (it doesnt work if u get rid of it)
            }
        }

        private boolean isInDistance(LivingEntity pEnemy) {
            return this.deepStrow.distanceTo(pEnemy) <= 1f;
        }
    }
}
