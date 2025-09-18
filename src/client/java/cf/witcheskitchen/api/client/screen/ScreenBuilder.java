package cf.witcheskitchen.api.client.screen;


import cf.witcheskitchen.WitchesKitchen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class ScreenBuilder {
    private static final ResourceLocation GUI_SLOT = ResourceLocation.withDefaultNamespace("container/slot");
    private static final ResourceLocation GUI_OUTPUT_SLOT = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "output_slot");
    private static final ResourceLocation GUI_CONTAINER = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "container");

    private static final ResourceLocation GUI_SMELTING_PROGRESS = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "smelting_progress");
    private static final ResourceLocation GUI_SMELTING_PROGRESS_FILLED = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "smelting_progress_filled");
    private static final ResourceLocation GUI_BREWING_PROGRESS = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "brewing_progress");
    private static final ResourceLocation GUI_BREWING_PROGRESS_FILLED = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "brewing_progress_filled");
    private static final ResourceLocation GUI_BURNING_PROGRESS = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "burning_progress");
    private static final ResourceLocation GUI_BURNING_PROGRESS_FILLED = ResourceLocation.fromNamespaceAndPath(WitchesKitchen.MODID, "burning_progress_filled");

    private final ScreenBase<?> parent;

    public ScreenBuilder(ScreenBase<?> parent) {
        this.parent = parent;
    }

    public static void drawPercentageTooltip(ScreenBase<?> base, GuiGraphics context, int x, int y, int width, int height, int xMouse, int yMouse, int value, int max) {
        x -= base.getX();
        y -= base.getY();
        if (base.isHovering(x, y, width, height, xMouse, yMouse)) {
            int percentage = scaledPercentageOf(value, max);
            context.setTooltipForNextFrame(Minecraft.getInstance().font, Component.literal(String.valueOf(percentage))
                .withStyle(percentageColor(percentage))
                .append("%"), xMouse, yMouse);
        }
    }

    public static int scaledPercentageOf(long value, long max) {
        if (value == 0) {
            return 0;
        } else {
            return (int) ((value * 100.0f) / max);
        }
    }

    /**
     * Device percentage formatting color
     */
    public static ChatFormatting percentageColor(int percentage) {
        if (percentage < 20) {
            return ChatFormatting.RED;
        } else if (percentage < 50) {
            return ChatFormatting.YELLOW;
        } else if (percentage < 75) {
            return ChatFormatting.GREEN;
        } else {
            return ChatFormatting.AQUA;
        }
    }

    public void drawContainer(final GuiGraphics context, final int left, final int top, final int width, final int height) {
        // spriteId, textureWidth, textureHeight, u, v, x, y, width, height
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_CONTAINER, 150, 150, 0, 0, left, top, width / 2, height / 2);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_CONTAINER, 150, 150, 150 - width / 2, 0, left + width / 2, top, width / 2, height / 2);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_CONTAINER, 150, 150, 0, 150 - height / 2, left, top + height / 2, width / 2, height / 2);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_CONTAINER, 150, 150, 150 - width / 2, 150 - height / 2, left + width / 2, top + height / 2, width / 2, height / 2);
    }

    /**
     * Draws the default player inventory slots
     *
     * @param context DrawContext
     * @param posX    originX
     * @param posY    originY
     */
    public void drawPlayerSlots(final GuiGraphics context, int posX, int posY) {
        // fixed position in minecraft
        posX += 7;
        posY += 83;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 9; y++) {
                context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_SLOT, posX + y * 18, posY + x * 18, 18, 18);
            }
        }
        final int offsetY = 58;
        for (int x = 0; x < 9; x++) {
            context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_SLOT, posX + x * 18, posY + offsetY, 18, 18);
        }
    }

    /**
     * Draws a single slot at the given position
     *
     * @param context DrawContext
     * @param posX    background posX
     * @param posY    background posY
     */
    public void drawSlot(final GuiGraphics context, final int posX, final int posY) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_SLOT, posX, posY, 18, 18);
    }

    /**
     * Draws an output slot at the given position
     *
     * @param context DrawContext
     * @param posX    background posX
     * @param posY    background posY
     */
    public void drawOutputSlot(final GuiGraphics context, final int posX, final int posY) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_OUTPUT_SLOT, posX, posY, 26, 26);
    }

    /**
     * Draws the vanilla smelting progress (arrow) at the given position.
     *
     * @param context     DrawContext
     * @param posX        gui posX
     * @param posY        gui posY
     * @param progress    current progress
     * @param maxProgress max progress
     */
    public void drawSmeltingProgress(GuiGraphics context, int posX, int posY, int progress, int maxProgress) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_SMELTING_PROGRESS, posX, posY, 22, 15);
        int i = (int) ((double) progress / (double) maxProgress * 23);
        i = Math.max(i, 0);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_SMELTING_PROGRESS_FILLED, posX, posY, i, 16);
    }

    /**
     * Draws the vanilla burning progress at the given position
     *
     * @param context     DrawContext
     * @param posX        gui posX
     * @param posY        gui posY
     * @param burning     whether the device is burning
     * @param progress    current burning progress
     * @param maxProgress max burning progress (depends on the stack fuel)
     */
    public void drawBurningProgress(GuiGraphics context, int posX, int posY, boolean burning, int progress, int maxProgress) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_BURNING_PROGRESS, posX, posY, 13, 13);
        int i = 12 - (int) ((double) progress / (double) maxProgress * 13); // 12 down to zero
        i = Math.max(i, 0);
        if (burning) {
            // FIXME: this might be broken?
            context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_BURNING_PROGRESS_FILLED, posX, posY, 14, 14 - i);
        }
    }

    /**
     * Draws the vanilla brewing progress at the given position and a tooltip with the percentage completed.
     *
     * @param context     DrawContext
     * @param posX        gui posX
     * @param posY        gui posY
     * @param mouseX      current mouseX position
     * @param mouseY      current mouseY position
     * @param progress    current progress
     * @param maxProgress max progress
     * @param brewing     whether the device is brewing
     */
    public void drawBrewingProgress(GuiGraphics context, int posX, int posY, int mouseX, int mouseY, int progress, int maxProgress, boolean brewing) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_BREWING_PROGRESS, posX, posY, 27, 27);
        int i = 26 - (int) ((double) progress / (double) maxProgress * 27); // 26 down to zero
        if (brewing) {
            // FIXME: this might be broken?
            context.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_BREWING_PROGRESS_FILLED, posX, posY, 11, 28 - i);
            ScreenBuilder.drawPercentageTooltip(this.parent, context, posX, posY, 11, 28, mouseX, mouseY, progress, maxProgress);
        }
    }

}
