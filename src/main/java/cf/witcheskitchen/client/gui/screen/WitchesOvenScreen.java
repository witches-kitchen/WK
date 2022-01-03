package cf.witcheskitchen.client.gui.screen;


import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class WitchesOvenScreen extends ScreenBase<WitchesOvenScreenHandler> {

    public WitchesOvenScreen(WitchesOvenScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawSlot(matrices, this.getX() + 43, this.getY() + 54);
        this.builder().drawSlot(matrices, this.getX() + 79, this.getY() + 54);
        this.builder().drawSlot(matrices, this.getX() + 43, this.getY() + 18);
        this.builder().drawOutputSlot(matrices, this.getX() + 111, this.getY() + 14);
        this.builder().drawSlot(matrices, this.getX() + 115, this.getY() + 54);
        this.builder().drawBurningProgress(matrices, this.getX() + 45, this.getY() + 38);
        this.builder().drawSmeltingProgress(matrices, this.getX() + 76, this.getY() + 19);
    }

    @Override
    protected void init() {
        super.init();
        this.titleY = 5;
    }
}
