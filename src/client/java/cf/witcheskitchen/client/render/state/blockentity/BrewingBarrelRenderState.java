package cf.witcheskitchen.client.render.state.blockentity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class BrewingBarrelRenderState extends BlockEntityRenderState {
    public Direction facing;
    public ItemStackRenderState itemStackState;
}
