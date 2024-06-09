package me.obsilabor.tpshud.mixin.client;

import me.obsilabor.tpshud.hud.TpsWidget;
import me.obsilabor.tpshud.screen.PositionSelectionScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "renderMiscOverlays", at = @At("HEAD"))
    private void renderTpsHud(DrawContext drawContext, float f, CallbackInfo ci) {
        if(!(MinecraftClient.getInstance().currentScreen instanceof PositionSelectionScreen)) {
            //MatrixStack matrixStack = new MatrixStack();
            drawContext.getMatrices().push();
            TpsWidget.INSTANCE.render(drawContext);
            drawContext.getMatrices().pop();
        }
    }
}
