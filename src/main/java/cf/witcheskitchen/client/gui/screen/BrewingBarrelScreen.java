package cf.witcheskitchen.client.gui.screen;

import cf.witcheskitchen.client.gui.screen.handler.BrewingBarrelScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class BrewingBarrelScreen extends ScreenBase<BrewingBarrelScreenHandler> {

    public BrewingBarrelScreen(BrewingBarrelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        // First row
        this.builder().drawSlot(matrices, this.getX() + 55, this.getY() + 29);
        this.builder().drawSlot(matrices, this.getX() + 74, this.getY() + 29);
        this.builder().drawSlot(matrices, this.getX() + 93, this.getY() + 29);
        // Second row
        this.builder().drawSlot(matrices, this.getX() + 55, this.getY() + 48);
        this.builder().drawSlot(matrices, this.getX() + 74, this.getY() + 48);
        this.builder().drawSlot(matrices, this.getX() + 93, this.getY() + 48);
        // Progress
        this.builder().drawBrewingProgress(matrices, this.getX() + 125, this.getY() + 35, mouseX, mouseY, handler.getProgressScaled(100), 100,  handler.isFermenting());
    }

}
