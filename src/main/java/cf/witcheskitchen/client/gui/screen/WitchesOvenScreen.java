package cf.witcheskitchen.client.gui.screen;


import cf.witcheskitchen.api.client.screen.ScreenBase;
import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class WitchesOvenScreen extends ScreenBase<WitchesOvenScreenHandler> {

    public WitchesOvenScreen(WitchesOvenScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawSlot(matrices, this.getX() + 43, this.getY() + 54);
        this.builder().drawSlot(matrices, this.getX() + 43, this.getY() + 18);
        this.builder().drawSlot(matrices, this.getX() + 115, this.getY() + 18);//output
        this.builder().drawSlot(matrices, this.getX() + 115, this.getY() + 54);//extra output
        this.builder().drawBurningProgress(matrices, this.getX() + 45, this.getY() + 38, handler.isBurning(), handler.getBurnTimeScaled(100), 100);
        this.builder().drawSmeltingProgress(matrices, this.getX() + 76, this.getY() + 19, handler.getProgressScaled(100), 100);
    }

    @Override
    protected void init() {
        super.init();
        this.titleY = 5;
    }
}
