package one.oth3r.caligo.entity.statue;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import one.oth3r.caligo.Caligo;

public class StatueEntity extends PathAwareEntity {
    public static final Identifier TEXTURE = new Identifier(Caligo.MOD_ID, "textures/entity/statue/statue.png");
    public StatueEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
