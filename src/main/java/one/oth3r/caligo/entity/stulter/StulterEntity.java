package one.oth3r.caligo.entity.stulter;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class StulterEntity extends HostileEntity {
    public StulterEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createStulterAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 6)
                .add(EntityAttributes.MOVEMENT_SPEED, .3)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.ATTACK_SPEED, 4)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE,.7);
    }
}
