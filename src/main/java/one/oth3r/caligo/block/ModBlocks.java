package one.oth3r.caligo.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.statue.StatueBlock;
import one.oth3r.caligo.block.statue.StatueBlockEntity;
import one.oth3r.caligo.block.statue.StatueBlockEntityRenderer;

public class ModBlocks {
    public static final Block STATUE_BLOCK = registerBlock("statue",
            new StatueBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY).requiresTool().dropsNothing().strength(1.5f, 6.0f)),false);
    public static final BlockEntityType<StatueBlockEntity> STATUE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Caligo.MOD_ID,"statue_block_entity"),
            BlockEntityType.Builder.create(StatueBlockEntity::new,STATUE_BLOCK).build());
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM,new Identifier(Caligo.MOD_ID,name),
                new BlockItem(block,new Item.Settings()));
    }
    private static Block registerBlock(String name, Block block, boolean item) {
        if (item) registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK,new Identifier(Caligo.MOD_ID,name),block);
    }
    public static void registerModBlocks() {
        
    }
    public static void registerClient() {
        BlockEntityRendererFactories.register(ModBlocks.STATUE_BLOCK_ENTITY, StatueBlockEntityRenderer::new);
    }
}
