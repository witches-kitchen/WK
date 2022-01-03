package cf.witcheskitchen.client.gui.screen;

import cf.witcheskitchen.client.gui.screen.builder.ScreenBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ScreenBase<T extends ScreenHandler> extends HandledScreen<T> {

    private final ScreenBuilder builder;

    public ScreenBase(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.builder = new ScreenBuilder(this);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.builder.drawContainer(matrices, this.x, this.y, this.backgroundWidth, this.backgroundHeight);
        this.builder.drawPlayerSlots(matrices, this.x, this.y);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        super.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    //centers title
    @Override
    protected void init() {
        super.init();
        this.titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        this.titleY = 14;
    }

    //getters for positions within our background
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    //background dimensions
    public int getWidth() {
        return this.backgroundWidth;
    }

    public int getHeight() {
        return this.backgroundHeight;
    }

    public ScreenBuilder builder() {
        return builder;
    }
}
