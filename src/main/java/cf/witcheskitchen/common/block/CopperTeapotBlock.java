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
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

public class CopperTeapotBlock extends TeapotBlock implements Oxidizable {
    private final Oxidizable.OxidizationLevel oxidizationLevel;

    public CopperTeapotBlock(Settings settings, Oxidizable.OxidizationLevel oxidizationLevel) {
        super(settings);
        this.oxidizationLevel = oxidizationLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        // Allow axe item interaction from Oxidizable
        if (stack.isOf(Items.HONEYCOMB) || stack.getItem() instanceof AxeItem) {
            return ActionResult.PASS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public OxidizationLevel getDegradationLevel() {
        return this.oxidizationLevel;
    }
}
