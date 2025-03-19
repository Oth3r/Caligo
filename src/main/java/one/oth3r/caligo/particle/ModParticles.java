package one.oth3r.caligo.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModParticles {
    // This DefaultParticleType gets called when you want to use your particle in code.
    public static final SimpleParticleType SNOWFLAKE_PARTICLE = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Caligo.MOD_ID, "snowflake_particle"), SNOWFLAKE_PARTICLE);
    }

    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(SNOWFLAKE_PARTICLE, SnowflakeParticle.Factory::new);
    }

}
