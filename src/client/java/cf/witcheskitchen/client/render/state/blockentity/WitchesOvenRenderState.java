package cf.witcheskitchen.client.render.state.blockentity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;

public class WitchesOvenRenderState extends BlockEntityRenderState {
    public Direction facing;
    public NonNullList<ItemStackRenderState> itemStackStates = NonNullList.withSize(4, new ItemStackRenderState());
}
