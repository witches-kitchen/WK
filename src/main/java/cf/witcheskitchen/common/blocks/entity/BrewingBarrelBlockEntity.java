package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.client.gui.screen.handler.BrewingBarrelScreenHandler;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrewingBarrelBlockEntity extends WKDeviceBlockEntity implements NamedScreenHandlerFactory {

    public BrewingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.BREWING_BARREL, pos, state, 6);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
    }



    @Override
    public Text getDisplayName() {
        return new TranslatableText("screen.title.witcheskitchen.brewing_barrel");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BrewingBarrelScreenHandler(syncId, inv, this);
    }
}
