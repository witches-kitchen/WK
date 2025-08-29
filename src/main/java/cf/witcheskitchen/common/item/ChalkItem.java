package cf.witcheskitchen.common.item;

import cf.witcheskitchen.common.block.GlyphBlock;
import cf.witcheskitchen.common.registry.WKBlocks;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ChalkItem extends Item {
    private final GlyphBlock glyphType;

    public ChalkItem(Properties settings, Block block) {
        super(settings);
        this.glyphType = (GlyphBlock) block;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPlaceContext placementContext = new BlockPlaceContext(context);
        BlockState state = glyphType.getStateForPlacement(placementContext);
        if (!world.getBlockState(pos).canBeReplaced(placementContext)) {
            pos = pos.relative(context.getClickedFace());
        }
        if (!world.getBlockState(pos).canBeReplaced(placementContext)) {
            return InteractionResult.PASS;
        }
        if (state != null && state.canSurvive(world, pos)) {
            if (!world.isClientSide()) {
                world.playSound(null, pos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1, Mth.nextFloat(world.random, 0.8f, 1.2f));
                world.setBlockAndUpdate(pos, state);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        if (glyphType != null) {
            String name = BuiltInRegistries.BLOCK.getKey(glyphType).getPath();
            int rgb = glyphType == WKBlocks.ENCHANTED_GLYPH ? 0xD8EAB4 : 0xffffff;
            textConsumer.accept(Component.translatable("tooltip.witcheskitchen." + name).setStyle(Style.EMPTY.withColor(rgb)));
        }
    }
}
