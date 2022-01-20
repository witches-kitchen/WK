package cf.witcheskitchen.client.gui.screen.builder;


import cf.witcheskitchen.WK;
import cf.witcheskitchen.client.gui.screen.ScreenBase;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public final class ScreenBuilder {

    private static final Identifier GUI_ELEMENTS = new Identifier(WK.MODID, "textures/gui/elements.png");
    private static final int SLOT_U = 237;
    private static final int SLOT_V = 1;
    private static final int OUTPUT_SLOT_U = 150;
    private static final int OUTPUT_SLOT_V = 16;
    private final ScreenBase<?> parent;

    public ScreenBuilder(ScreenBase<?> parent) {
        this.parent = parent;
    }

    // We call this whenever we draw something on the screen
    // Because maybe it doesn't already have the texture bound
    private static void bindTexture() {
        RenderSystem.setShaderTexture(0, ScreenBuilder.GUI_ELEMENTS);
    }

    public static void drawPercentageTooltip(ScreenBase<?> base, MatrixStack stack, int x, int y, int width, int height, int xMouse, int yMouse, int value, int max) {
        x -= base.getX();
        y -= base.getY();
        if (base.isPointWithinBounds(x, y, width, height, xMouse, yMouse)) {
            int percentage = scaledPercentageOf(value, max);
            base.renderTooltip(stack, new LiteralText(String.valueOf(percentage))
                    .formatted(percentageColor(percentage))
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

    public static Formatting percentageColor(int percentage) {
        if (percentage < 20) {
            return Formatting.RED;
        } else if (percentage < 50) {
            return Formatting.YELLOW;
        } else if (percentage < 75) {
            return Formatting.GREEN;
        } else {
            return Formatting.AQUA;
        }
    }

    public void drawContainer(final MatrixStack stack, final int left, final int top, final int width, final int height) {
        bindTexture();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.parent.drawTexture(stack, left, top, 0, 0, width / 2, height / 2);
        this.parent.drawTexture(stack, left + width / 2, top, 150 - width / 2, 0, width / 2, height / 2);
        this.parent.drawTexture(stack, left, top + height / 2, 0, 150 - height / 2, width / 2, height / 2);
        this.parent.drawTexture(stack, left + width / 2, top + height / 2, 150 - width / 2, 150 - height / 2, width / 2, height / 2);
    }

    /**
     * Draws the default player inventory slots
     *
     * @param matrixStack MatrixStack
     * @param posX        originX
     * @param posY        originY
     */
    public void drawPlayerSlots(final MatrixStack matrixStack, int posX, int posY) {
        //fixed position in minecraft
        posX += 7;
        posY += 83;
        bindTexture();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 9; y++) {
                this.parent.drawTexture(matrixStack, posX + y * 18, posY + x * 18, SLOT_U, SLOT_V, 18, 18);
            }
        }
        final int offsetY = 58;
        for (int x = 0; x < 9; x++) {
            this.parent.drawTexture(matrixStack, posX + x * 18, posY + offsetY, SLOT_U, SLOT_V, 18, 18);
        }
    }

    /**
     * Draws a single slot at the given position
     *
     * @param matrixStack MatrixStack
     * @param posX        background posX
     * @param posY        background posY
     */
    public void drawSlot(final MatrixStack matrixStack, final int posX, final int posY) {
        bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, SLOT_U, SLOT_V, 18, 18);
    }

    /**
     * Draws an output slot at the given position
     *
     * @param matrixStack MatrixStack
     * @param posX        background posX
     * @param posY        background posY
     */
    public void drawOutputSlot(final MatrixStack matrixStack, final int posX, final int posY) {
        bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, OUTPUT_SLOT_U, OUTPUT_SLOT_V, 26, 26);
    }

    /**
     * Draws the vanilla smelting progress (arrow) at the given position.
     *
     * @param matrixStack MatrixStack
     * @param posX        gui posX
     * @param posY        gui posY
     * @param progress    current progress
     * @param maxProgress max progress
     */
    public void drawSmeltingProgress(MatrixStack matrixStack, int posX, int posY, int progress, int maxProgress) {
        bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, 150, 0, 22, 15);
        int i = (int) ((double) progress / (double) maxProgress * 23);
        i = Math.max(i, 0);
        this.parent.drawTexture(matrixStack, posX, posY, 172, 0, i, 16);
    }

    /**
     * Draws the vanilla burning progress at the given position
     *
     * @param matrixStack MatrixStack
     * @param posX        gui posX
     * @param posY        gui posY
     * @param burning     whether the device is burning
     * @param progress    current burning progress
     * @param maxProgress max burning progress (depends on the stack fuel)
     */
    public void drawBurningProgress(MatrixStack matrixStack, int posX, int posY, boolean burning, int progress, int maxProgress) {
        bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, 239, 34, 13, 13);
        int i = 12 - (int) ((double) progress / (double) maxProgress * 13); // 12 down to zero
        i = Math.max(i, 0);
        if (burning) {
            this.parent.drawTexture(matrixStack, posX, posY + i, 239, 19 + i, 14, 14 - i);
        }
    }

    /**
     * Draws the vanilla brewing progress at the given position and a tooltip with the percentage completed.
     *
     * @param matrixStack MatrixStack
     * @param posX        gui posX
     * @param posY        gui posY
     * @param mouseX      current mouseX position
     * @param mouseY      current mouseY position
     * @param progress    current progress
     * @param maxProgress max progress
     * @param brewing     whether the device is brewing
     */
    public void drawBrewingProgress(MatrixStack matrixStack, int posX, int posY, int mouseX, int mouseY, int progress, int maxProgress, boolean brewing) {
        bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, 151, 43, 10, 27);
        int i = 26 - (int) ((double) progress / (double) maxProgress * 27); // 26 down to zero
        if (brewing) {
            this.parent.drawTexture(matrixStack, posX, posY + i, 163, 43 + i, 11, 28 - i);
            ScreenBuilder.drawPercentageTooltip(this.parent, matrixStack, posX, posY, 11, 28, mouseX, mouseY, progress, maxProgress);
        }
    }

}
