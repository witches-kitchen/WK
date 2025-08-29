package cf.witcheskitchen.client.gui.screen;


import cf.witcheskitchen.api.client.screen.ScreenBase;
import cf.witcheskitchen.common.screenhandler.WitchesOvenScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WitchesOvenScreen extends ScreenBase<WitchesOvenScreenHandler> {

    public WitchesOvenScreen(WitchesOvenScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        super.renderBg(context, delta, mouseX, mouseY);
        this.builder().drawSlot(context, this.getX() + 43, this.getY() + 54);
        this.builder().drawSlot(context, this.getX() + 43, this.getY() + 18);
        this.builder().drawSlot(context, this.getX() + 115, this.getY() + 18);//output
        this.builder().drawSlot(context, this.getX() + 115, this.getY() + 54);//extra output
        this.builder().drawBurningProgress(context, this.getX() + 45, this.getY() + 38, menu.isBurning(), menu.getBurnTimeScaled(100), 100);
        this.builder().drawSmeltingProgress(context, this.getX() + 76, this.getY() + 19, menu.getProgressScaled(100), 100);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 5;
    }
}
