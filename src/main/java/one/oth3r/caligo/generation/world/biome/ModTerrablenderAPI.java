package one.oth3r.caligo.generation.world.biome;

import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.generation.world.biome.surface.IceCavesSurfaceRule;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerrablenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegions(Identifier.of(Caligo.MOD_ID, "overworld_1"), 4));

        // register surface rules
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Caligo.MOD_ID, IceCavesSurfaceRule.makeRules());
    }
}
