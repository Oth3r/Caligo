package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.*;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.generation.data.ModModels;
import one.oth3r.caligo.generation.data.providers.ModModelProvider;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.loot_table.ModLootTables;
import one.oth3r.caligo.tag.ModBlockTags;
import one.oth3r.caligo.tag.ModItemTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static net.minecraft.client.data.BlockStateModelGenerator.createSingletonBlockState;

/**
 * providers for the strow, and everything to do with it;
 * strow, statue
 */
public class StrowProviders {
    public static final ArrayList<Block> PICKAXE_MINEABLE = new ArrayList<>(Arrays.asList(ModBlocks.STATUE_BLOCK,ModBlocks.DEEPSLATE_STATUE_BLOCK,
            ModBlocks.LUMIN_CRYSTAL_BLOCK));
    public static final ArrayList<Block> NEEDS_IRON_TOOL = new ArrayList<>(PICKAXE_MINEABLE);

    private static CompletableFuture<RegistryWrapper.WrapperLookup> regLookup;

    public static class EntityLootTable extends SimpleFabricLootTableProvider {
        public EntityLootTable(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup, LootContextTypes.ENTITY);
            regLookup = registryLookup;
        }

        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            try {
                lootTableBiConsumer.accept(ModLootTables.STROW, LootTable.builder()
                        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1F))
                                .with(ItemEntry.builder(ModItems.STROW_ESSENCE)
                                        .apply(SetCountLootFunction.builder(BinomialLootNumberProvider.create(1, 0.65F)))
                                        .apply(new EnchantedCountIncreaseLootFunction.Builder(regLookup.get().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),UniformLootNumberProvider.create(0,1)))))
                        .randomSequenceId(ModLootTables.STROW.getRegistry()));


                lootTableBiConsumer.accept(ModLootTables.DEEP_STROW, LootTable.builder()
                        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1F))
                                .with(ItemEntry.builder(ModItems.STROW_ESSENCE)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                        .apply(new EnchantedCountIncreaseLootFunction.Builder(regLookup.get().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),UniformLootNumberProvider.create(0,1)))))
                        .randomSequenceId(ModLootTables.DEEP_STROW.getRegistry()));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getName() {
            return "Strow "+super.getName();
        }
    }

    public static class Model {
        public static void generateBlockStateModels(BlockStateModelGenerator bGenerator) {
            // statue
            bGenerator.blockStateCollector.accept(createSingletonBlockState(ModBlocks.STATUE_BLOCK,
                    ModModels.STATUE_BLOCK.upload(ModBlocks.STATUE_BLOCK,
                            new TextureMap().put(TextureKey.ALL, TextureMap.getId(Blocks.STONE)), bGenerator.modelCollector)));
            bGenerator.blockStateCollector.accept(createSingletonBlockState(ModBlocks.DEEPSLATE_STATUE_BLOCK,
                    ModModels.STATUE_BLOCK.upload(ModBlocks.DEEPSLATE_STATUE_BLOCK,
                            new TextureMap().put(TextureKey.ALL, TextureMap.getId(Blocks.DEEPSLATE)), bGenerator.modelCollector)));

            // lumin crystal
            bGenerator.blockStateCollector
                    .accept(VariantsBlockStateSupplier.create(ModBlocks.LUMIN_CRYSTAL_BLOCK,
                                    BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(Caligo.MOD_ID,"block/lumin_crystal")))
                            .coordinate(bGenerator.createUpDefaultFacingVariantMap()));
        }

        public static void generateItemModels(ItemModelGenerator iGenerator) {
            // statue
            iGenerator.register(ModItems.STATUE, ModModels.STATUE_ITEM);
            iGenerator.register(ModItems.DEEPSLATE_STATUE, ModModels.STATUE_ITEM);

            // strow essence
            iGenerator.register(ModItems.STROW_ESSENCE, Models.GENERATED);

            // SPAWN EGGS
            iGenerator.registerSpawnEgg(ModItems.STROW_SPAWN_EGG, 0x808080, 0xa0a0a0);
            iGenerator.registerSpawnEgg(ModItems.DEEP_STROW_SPAWN_EGG, 0x33333b, 0x797979);

            // lumin crystal
            iGenerator.register(ModItems.LUMIN_CRYSTAL, ModModelProvider.getBlockItem("lumin_crystal"));
        }
    }

    public static class ItemTag extends FabricTagProvider.ItemTagProvider {

        public ItemTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModItemTags.STATUES)
                    .add(ModItems.STATUE)
                    .add(ModItems.DEEPSLATE_STATUE);
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }

    public static class BlockTag extends FabricTagProvider.BlockTagProvider {

        public BlockTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModBlockTags.STATUES)
                    .add(ModBlocks.STATUE_BLOCK)
                    .add(ModBlocks.DEEPSLATE_STATUE_BLOCK);
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }

    public static class Recipe extends FabricRecipeProvider {

        public Recipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            return new RecipeGenerator(registryLookup, exporter) {
                @Override
                public void generate() {
                    createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STATUE_BLOCK)
                            .pattern(" E ").pattern("SSS").pattern(" S ")
                            .input('S', Items.STONE)
                            .input('E', ModItems.STROW_ESSENCE)
                            .criterion(hasItem(ModItems.STROW_ESSENCE),
                                    conditionsFromItem(ModItems.STROW_ESSENCE))
                            .offerTo(exporter);

                    createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEEPSLATE_STATUE_BLOCK)
                            .pattern(" E ").pattern("SSS").pattern(" S ")
                            .input('S', Items.DEEPSLATE)
                            .input('E', ModItems.STROW_ESSENCE)
                            .criterion(hasItem(ModItems.STROW_ESSENCE),
                                    conditionsFromItem(ModItems.STROW_ESSENCE))
                            .offerTo(exporter);

                    createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LUMIN_CRYSTAL_BLOCK)
                            .pattern(" A ").pattern("SES").pattern("SAS")
                            .input('A', Items.AMETHYST_SHARD)
                            .input('S', Items.STONE)
                            .input('E', ModItems.STROW_ESSENCE)
                            .criterion(hasItem(ModItems.STROW_ESSENCE), conditionsFromItem(ModItems.STROW_ESSENCE))
                            .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                            .offerTo(exporter);
                }
            };
        }

        @Override
        public String getName() {
            return "Statue Recipe Gen";
        }
    }

    public static class BlockLootTable extends FabricBlockLootTableProvider {

        public BlockLootTable(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.STATUE_BLOCK, statueDrop(ModBlocks.STATUE_BLOCK));
            addDrop(ModBlocks.DEEPSLATE_STATUE_BLOCK, statueDrop(ModBlocks.DEEPSLATE_STATUE_BLOCK));

            // lumin crystal
            addDrop(ModBlocks.LUMIN_CRYSTAL_BLOCK);
        }

        public LootTable.Builder statueDrop(Block statue) {
            RegistryEntryLookup<EntityType<?>> registryEntryLookup = this.registries.getOrThrow(RegistryKeys.ENTITY_TYPE);

            return new LootTable.Builder().pool(addSurvivesExplosionCondition(statue, LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(statue)
                            .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                    EntityPredicate.Builder.create().type(registryEntryLookup, EntityType.PLAYER))))));
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }
}
