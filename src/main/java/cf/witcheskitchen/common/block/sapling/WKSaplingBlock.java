package cf.witcheskitchen.common.block.sapling;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WKSaplingBlock extends SaplingBlock {

    public WKSaplingBlock(TreeGrower generator, BlockBehaviour.Properties settings) {
        super(generator, settings);
    }
}