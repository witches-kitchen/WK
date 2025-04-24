package cf.witcheskitchen.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CopperWitchesOvenBlock extends WitchesOvenBlock implements Oxidizable {
    private final Oxidizable.OxidationLevel oxidizationLevel;

    public CopperWitchesOvenBlock(Settings settings, Oxidizable.OxidationLevel oxidizationLevel) {
        super(settings);
        this.oxidizationLevel = oxidizationLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Allow axe item interaction from Oxidizable
        if (stack.isOf(Items.HONEYCOMB) || stack.getItem() instanceof AxeItem) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return this.oxidizationLevel;
    }
}
