package one.oth3r.caligo.entity.cryonix;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import one.oth3r.caligo.particle.ModParticles;

public class CryonixEntity extends ZombieEntity {
    private static final int CRACK_TIME = 80;
    private static final TrackedData<Integer> CRACKED = DataTracker.registerData(CryonixEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public CryonixEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCryonixAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.21F)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.5)
                .add(EntityAttributes.ARMOR, 1.6)
                .add(EntityAttributes.SPAWN_REINFORCEMENTS);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CRACKED,0);
    }

    public int getCracked() {
        return this.dataTracker.get(CRACKED);
    }
    public boolean isCracked() {
        return this.getCracked() > 0;
    }
    public void setCracked(int cracked) {
        this.dataTracker.set(CRACKED, cracked);
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        boolean result = super.damage(world, source, amount);
        if (!this.isCracked()) {
            this.playSound(SoundEvents.BLOCK_GLASS_BREAK,.25f, (float) random.nextBetween(98, 102) / 100);
        }
        setCracked(CRACK_TIME);
        return result;
    }

    @Override
    public void tick() {
        super.tick();
        // tick crack time
        if (this.isCracked()) {
            this.setCracked(this.getCracked() - 1);

            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.SPEED, getCracked(), 1, false, false);
            addStatusEffect(statusEffectInstance);
        }
    }

    @Override
    public void tickMovement() {
        World word = this.getWorld();
        if (word.isClient) {
            if (word.getTime() % 2 == 0) {
                double
                        x = this.getParticleX(0.5),
                        y = this.getBodyY(this.getHeight() * ((double) random.nextBetween(20, 50) /100)),
                        z = this.getParticleZ(0.5),
                        velX = this.random.nextInt(1) / 100.,
                        velY = -(this.random.nextInt(10) / 100.),
                        velZ = this.random.nextInt(1) / 100.;
                if (this.isCracked()) {
                    if (this.random.nextBoolean()) {
                        word.addParticle(
                                ParticleTypes.FALLING_WATER,
                                x, y, z, velX, velY, velZ);
                    } else {
                        word.addParticle(
                                ParticleTypes.FALLING_DRIPSTONE_WATER,
                                x, y, z, velX, velY, velZ);
                    }

                } else {
                    word.addParticle(
                            ModParticles.SNOWFLAKE_PARTICLE,
                            x, y, z, velX, velY, velZ);
                }

            }
        }
        super.tickMovement();
    }
}
