package one.oth3r.caligo.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.statue.deepslate.DeepslateStatueBlock;
import one.oth3r.caligo.block.lumin_crystal.LuminCrystalBlock;
import one.oth3r.caligo.block.lumin_crystal.LuminCrystalBlockEntity;
import one.oth3r.caligo.block.statue.StatueBlock;
import one.oth3r.caligo.block.statue.StatueBlockEntity;
import one.oth3r.caligo.block.statue.StatueBlockEntityRenderer;

import java.util.function.ToIntFunction;

public class ModBlocks {

    // STATUE

    public static final Block STATUE_BLOCK = registerBlock("statue",
            new StatueBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6.0f)));

    public static final Block DEEPSLATE_STATUE_BLOCK = registerBlock("deepslate_statue",
            new DeepslateStatueBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY).requiresTool().strength(1.5f, 6.0f)));

    public static final BlockEntityType<StatueBlockEntity> STATUE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Caligo.MOD_ID,"statue_block_entity"),
            BlockEntityType.Builder.create(StatueBlockEntity::new,STATUE_BLOCK,DEEPSLATE_STATUE_BLOCK).build());

    // LUMIN CRYSTAL

    public static final ToIntFunction<BlockState> toInt = value -> value.get(LuminCrystalBlock.POWER);

    public static final Block LUMIN_CRYSTAL_BLOCK = registerBlock("lumin_crystal",
            new LuminCrystalBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY).luminance(toInt).requiresTool().strength(1.5f, 6.0f)));

    public static final BlockEntityType<LuminCrystalBlockEntity> LUMIN_CRYSTAL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Caligo.MOD_ID,"lumin_crystal_block_entity"),
            BlockEntityType.Builder.create(LuminCrystalBlockEntity::new, LUMIN_CRYSTAL_BLOCK).build());

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK,Identifier.of(Caligo.MOD_ID,name),block);
    }

    public static void registerModBlocks() {
        
    }

    public static void registerClient() {
        BlockEntityRendererFactories.register(STATUE_BLOCK_ENTITY, StatueBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(LUMIN_CRYSTAL_BLOCK, RenderLayer.getCutout());
    }
}
