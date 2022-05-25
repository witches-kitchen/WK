package cf.witcheskitchen.common.blocks.sapling;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;

public class WKSaplingBlock extends SaplingBlock {

    public WKSaplingBlock(SaplingGenerator generator, FabricBlockSettings settings) {
        super(generator, settings);
    }
}