package cf.witcheskitchen.client.gui.screen.builder;


import cf.witcheskitchen.WK;
import cf.witcheskitchen.client.gui.screen.ScreenBase;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
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

    public void drawSmeltingProgress(MatrixStack matrixStack, int posX, int posY, int progress, int maxProgress) {
        bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, 150, 0, 22, 15);
        int i = (int) ((double) progress / (double) maxProgress * 23);
        i = Math.max(i, 0);
        this.parent.drawTexture(matrixStack, posX, posY, 172, 0, i, 16);

    }

    public void drawBurningProgress(MatrixStack matrixStack, int posX, int posY, boolean burning, int progress, int maxProgress) {
        bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, 239, 34, 13, 13);
        int i = 12 - (int) ((double) progress / (double) maxProgress * 13);
        i = Math.max(i, 0);
        if (burning) {
            this.parent.drawTexture(matrixStack, posX, posY + i, 239, 19 + i, 14, 14 - i);
        }
    }
}
