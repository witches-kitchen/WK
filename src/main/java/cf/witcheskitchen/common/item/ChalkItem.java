package cf.witcheskitchen.common.item;

import cf.witcheskitchen.common.block.GlyphBlock;
import cf.witcheskitchen.common.registry.WKBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.List;

public class ChalkItem extends Item {
    private final GlyphBlock glyphType;

    public ChalkItem(Settings settings, Block block) {
        super(settings);
        this.glyphType = (GlyphBlock) block;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemPlacementContext placementContext = new ItemPlacementContext(context);
        BlockState state = glyphType.getPlacementState(placementContext);
        if (!world.getBlockState(pos).canReplace(placementContext)) {
            pos = pos.offset(context.getSide());
        }
        if (!world.getBlockState(pos).canReplace(placementContext)) {
            return ActionResult.PASS;
        }
        if (state != null && state.canPlaceAt(world, pos)) {
            if (!world.isClient()) {
                world.playSound(null, pos, state.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1, MathHelper.nextFloat(world.random, 0.8f, 1.2f));
                world.setBlockState(pos, state);
            }
            return ActionResult.success(true);
        }
        return super.useOnBlock(context);
    }

    @ClientOnly
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (glyphType != null) {
            String name = Registries.BLOCK.getId(glyphType).getPath();
            int rgb = glyphType == WKBlocks.ENCHANTED_GLYPH ? 0xD8EAB4 : 0xffffff;
            tooltip.add(Text.translatable("tooltip.witcheskitchen." + name).setStyle(Style.EMPTY.withColor(rgb)));
        }
    }
}
