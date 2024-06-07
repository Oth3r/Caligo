package one.oth3r.caligo.entity.statue;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import one.oth3r.caligo.Caligo;

public class StatueEntity extends PathAwareEntity {
    public static final Identifier TEXTURE_NORMAL = new Identifier(Caligo.MOD_ID, "textures/block/statue/statue.png");
    public static final Identifier TEXTURE_DEEP = new Identifier(Caligo.MOD_ID, "textures/block/statue/deepslate_statue.png");
    public StatueEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
