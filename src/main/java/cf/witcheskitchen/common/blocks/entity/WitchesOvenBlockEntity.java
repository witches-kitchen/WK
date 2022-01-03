package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class WitchesOvenBlockEntity extends WKDeviceBlockEntity implements NamedScreenHandlerFactory {

    public WitchesOvenBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_OVEN, pos, state, 5);
    }

    public int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(stack.getItem(), 0);
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new WitchesOvenScreenHandler(syncId, inv, this);
    }
}
