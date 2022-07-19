package cf.witcheskitchen.common.blocks.sapling;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class WKSaplingBlock extends SaplingBlock {

    public WKSaplingBlock(SaplingGenerator generator, QuiltBlockSettings settings) {
        super(generator, settings);
    }
}