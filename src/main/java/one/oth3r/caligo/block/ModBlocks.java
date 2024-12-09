package one.oth3r.caligo.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.plant.dripleaf_vine.DripleafVineBlock;
import one.oth3r.caligo.block.plant.dripleaf_vine.DripleafVinePlantBlock;
import one.oth3r.caligo.block.plant.LushMarigoldFlowerBlock;
import one.oth3r.caligo.block.plant.petunia.PetuniaFlowerBlock;
import one.oth3r.caligo.block.plant.petunia.PetuniaBlock;
import one.oth3r.caligo.block.statue.deepslate.DeepslateStatueBlock;
import one.oth3r.caligo.block.lumin_crystal.LuminCrystalBlock;
import one.oth3r.caligo.block.lumin_crystal.LuminCrystalBlockEntity;
import one.oth3r.caligo.block.statue.StatueBlock;
import one.oth3r.caligo.block.statue.StatueBlockEntity;
import one.oth3r.caligo.block.statue.StatueBlockEntityRenderer;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ModBlocks {

    // STATUE
    public static final Block STATUE_BLOCK = registerBlock("statue",
            StatueBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6.0f));

    public static final Block DEEPSLATE_STATUE_BLOCK = registerBlock("deepslate_statue",
            DeepslateStatueBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY).requiresTool().strength(1.5f, 6.0f));

    public static final BlockEntityType<StatueBlockEntity> STATUE_BLOCK_ENTITY = registerBlockEntity(
            "statue_block_entity", StatueBlockEntity::new, STATUE_BLOCK, DEEPSLATE_STATUE_BLOCK);


    // LUMIN CRYSTAL
    public static final ToIntFunction<BlockState> toInt = value -> value.get(LuminCrystalBlock.POWER);

    public static final Block LUMIN_CRYSTAL_BLOCK = registerBlock("lumin_crystal",
            LuminCrystalBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY).luminance(toInt).requiresTool().strength(1.5f, 6.0f));

    public static final BlockEntityType<LuminCrystalBlockEntity> LUMIN_CRYSTAL_BLOCK_ENTITY = registerBlockEntity(
            "lumin_crystal_block_entity", LuminCrystalBlockEntity::new, LUMIN_CRYSTAL_BLOCK);


    // DRIPLEAF VINE
    public static final Block DRIPLEAF_VINES = registerBlock("dripleaf_vines",
            DripleafVineBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN).breakInstantly().noCollision()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .sounds(BlockSoundGroup.BIG_DRIPLEAF));
    public static final Block DRIPLEAF_VINES_PLANT = registerBlock("dripleaf_vines_plant",
            DripleafVinePlantBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN).breakInstantly().noCollision()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .sounds(BlockSoundGroup.BIG_DRIPLEAF));

    // PETUNIA BLOCK
    public static final Block PETUNIA_FLOWER = registerBlock("petunia_stem",
            PetuniaFlowerBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIME).breakInstantly().noCollision()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .sounds(BlockSoundGroup.VINE));
    public static final Block PETUNIA = registerBlock("petunia",
            PetuniaBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.PINK).breakInstantly().noCollision()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .sounds(BlockSoundGroup.VINE));
    public static final Block POTTED_PETUNIA = registerBlock("potted_petunia",
            settings -> new FlowerPotBlock(PETUNIA, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.PINK).breakInstantly().nonOpaque()
                    .pistonBehavior(PistonBehavior.DESTROY));

    // LUSH MARIGOLD
    public static final Block LUSH_MARIGOLD = registerBlock("lush_marigold",
            LushMarigoldFlowerBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW).breakInstantly().noCollision()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .offset(AbstractBlock.OffsetType.XZ).sounds(BlockSoundGroup.GRASS));
    public static final Block POTTED_LUSH_MARIGOLD = registerBlock("potted_lush_marigold",
            settings -> new FlowerPotBlock(LUSH_MARIGOLD, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW).breakInstantly().nonOpaque()
                    .pistonBehavior(PistonBehavior.DESTROY));

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Caligo.MOD_ID, id));
    }

    /// ICE CAVES
    public static final Block FROSTED_STONE = registerBlock("frosted_stone",
            Block::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(1.5F, 6.0F));

    public static final Block FROSTED_DEEPSLATE = registerBlock("frosted_deepslate",
            PillarBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(3.0F, 6.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE));

    public static final Block SNOW_PATH = registerBlock("snow_path",
            SnowPathBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_BLUE_GRAY)
                    .requiresTool()
                    .sounds(BlockSoundGroup.SNOW)); //todo sound

    public static final Block COMPACTED_SNOW = registerBlock("compacted_snow",
            Block::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_BLUE_GRAY)
                    .requiresTool().strength(0.8F,.8F)
                    .sounds(BlockSoundGroup.SNOW)); //todo sound


    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return Blocks.register(keyOf(name), factory, settings);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(
            String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE,Identifier.of(Caligo.MOD_ID,name),
                FabricBlockEntityTypeBuilder.create(factory,blocks).build());
    }

    public static void registerModBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(DRIPLEAF_VINES_PLANT,100, 60);
        registry.add(DRIPLEAF_VINES,100, 60);
        registry.add(PETUNIA_FLOWER,100, 60);
        registry.add(PETUNIA,100, 60);
        registry.add(LUSH_MARIGOLD,100, 60);
    }

    public static void registerClient() {
        BlockEntityRendererFactories.register(STATUE_BLOCK_ENTITY, StatueBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(LUMIN_CRYSTAL_BLOCK, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(DRIPLEAF_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DRIPLEAF_VINES_PLANT, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(PETUNIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PETUNIA_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(POTTED_PETUNIA, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(LUSH_MARIGOLD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(POTTED_LUSH_MARIGOLD, RenderLayer.getCutout());
    }
}
