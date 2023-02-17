package cf.witcheskitchen.api.block;

import cf.witcheskitchen.api.block.entity.IExperienceHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


/**
 * Represents a device.
 *
 * <p>
 * A device has the following components and capabilities:
 * </p>
 * <p>
 * - Block Entity
 * </p>
 * - Ticking
 * <p>
 * - Inventory
 * </p>
 * - ScreenHandler (aka container)
 * <p>
 * - Screen (aka GUI)
 * </p>
 * <p>
 * - May or may not drop experience
 * </p>
 * <p>
 * If your block is not intended to have these components you may consider
 * extending the parent class instead.
 * </p>
 */
@SuppressWarnings("deprecation")
public abstract class WKBlock extends WKBlockWithEntity {

    protected WKBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final BlockEntity entity = world.getBlockEntity(pos);
        // We extended BlockEntityProvider.
        // But i've seen weird bugs
        if (entity == null) {
            return ActionResult.PASS;
        }
        // Requests a screen
        if (entity instanceof NamedScreenHandlerFactory factory) {
            player.openHandledScreen(factory);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            final BlockEntity entity = world.getBlockEntity(pos);
            if (world instanceof ServerWorld serverWorld) {
                if (entity instanceof Inventory inventory) {
                    ItemScatterer.spawn(world, pos, inventory);
                }
                if (entity instanceof IExperienceHandler handler) {
                    handler.dropExperience(serverWorld, Vec3d.of(pos));
                }
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

}
