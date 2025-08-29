package cf.witcheskitchen.api.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Environment(EnvType.CLIENT)
public abstract class ScreenBase<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    private final ScreenBuilder builder;

    public ScreenBase(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.builder = new ScreenBuilder(this);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        this.builder.drawContainer(context, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
        this.builder.drawPlayerSlots(context, this.leftPos, this.topPos);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        super.renderTooltip(context, mouseX, mouseY);
    }

    // Centers title
    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (imageWidth - font.width(title)) / 2;
        this.titleLabelY = 14;
    }

    // Public access
    @Override
    public boolean isHovering(int x, int y, int width, int height, double pointX, double pointY) {
        return super.isHovering(x, y, width, height, pointX, pointY);
    }

    // Getters for positions within our background
    public int getX() {
        return this.leftPos;
    }

    public int getY() {
        return this.topPos;
    }

    // Background dimensions
    public int getWidth() {
        return this.imageWidth;
    }

    public int getHeight() {
        return this.imageHeight;
    }

    public ScreenBuilder builder() {
        return builder;
    }
}
