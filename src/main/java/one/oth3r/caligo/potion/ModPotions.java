package one.oth3r.caligo.potion;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.item.ModItems;

public class ModPotions {
    public static final Potion PETRIFIED_POTION = registerPotion(
            new Potion("petrified", new StatusEffectInstance(ModEffects.getEffect(ModEffects.PETRIFIED), 900, 0)));
    public static final Potion PETRIFIED_POTION_LONG = registerPotion(
            new Potion("petrified_extended", new StatusEffectInstance(ModEffects.getEffect(ModEffects.PETRIFIED), 1800, 0)));
    public static final Potion PETRIFIED_POTION_STRONG = registerPotion(
            new Potion("petrified_amplified", new StatusEffectInstance(ModEffects.getEffect(ModEffects.PETRIFIED), 440, 1)));

    public static Potion registerPotion(Potion potion) {
        return Registry.register(Registries.POTION, Identifier.of(Caligo.MOD_ID,potion.getBaseName()),potion);
    }
    public static void register() {
        registerRecipes();
    }

    public static RegistryEntry<Potion> getPotion(Potion potion) {
        return Registries.POTION.getEntry(potion);
    }

    private static void registerRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.THICK, ModItems.STROW_ESSENCE, getPotion(PETRIFIED_POTION));
            builder.registerPotionRecipe(getPotion(PETRIFIED_POTION), Items.REDSTONE, getPotion(PETRIFIED_POTION_LONG));
            builder.registerPotionRecipe(getPotion(PETRIFIED_POTION), Items.GLOWSTONE, getPotion(PETRIFIED_POTION_STRONG));
        });
    }

}
