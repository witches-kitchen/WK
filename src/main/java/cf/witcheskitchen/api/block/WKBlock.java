package cf.witcheskitchen.api.block;

import cf.witcheskitchen.api.block.entity.IExperienceHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;


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

    protected WKBlock(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        final BlockEntity entity = world.getBlockEntity(pos);
        // We extended BlockEntityProvider.
        // But i've seen weird bugs
        if (entity == null) {
            return InteractionResult.PASS;
        }
        // Requests a screen
        if (entity instanceof MenuProvider factory) {
            player.openMenu(factory);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        final BlockEntity entity = world.getBlockEntity(pos);
        if (world instanceof ServerLevel serverWorld) {
            if (entity instanceof Container inventory) {
                Containers.dropContents(world, pos, inventory);
            }
            if (entity instanceof IExperienceHandler handler) {
                handler.dropExperience(serverWorld, Vec3.atLowerCornerOf(pos));
            }
        }
        world.updateNeighbourForOutputSignal(pos, this);
        super.affectNeighborsAfterRemoval(state, world, pos, moved);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

}
