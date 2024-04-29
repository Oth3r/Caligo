package one.oth3r.caligo.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.item.ModItems;

public class ModPotions {
    public static final Potion PETRIFIED_POTION = registerPotion("petrified",
            new Potion(new StatusEffectInstance(ModEffects.PETRIFIED, 900, 0)));
    public static final Potion PETRIFIED_POTION_LONG = registerPotion("petrified_extended",
            new Potion(new StatusEffectInstance(ModEffects.PETRIFIED, 1800, 0)));
//    public static final Potion PETRIFIED_POTION_STRONG = registerPotion("petrified_amplified",
//            new Potion(new StatusEffectInstance(ModEffects.PETRIFIED, 440, 2)));
    public static Potion registerPotion(String name, Potion potion) {
        return Registry.register(Registries.POTION, new Identifier(Caligo.MOD_ID,name),potion);
    }
    public static void register() {
        registerRecipes();
    }
    private static void registerRecipes() {
        BrewingRecipeRegistry.registerPotionRecipe(Potions.THICK, ModItems.STROW_ESSENCE, PETRIFIED_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(PETRIFIED_POTION, Items.REDSTONE, PETRIFIED_POTION_LONG);
//        BrewingRecipeRegistry.registerPotionRecipe(PETRIFIED_POTION, Items.GLOWSTONE, PETRIFIED_POTION_STRONG);
    }

}
