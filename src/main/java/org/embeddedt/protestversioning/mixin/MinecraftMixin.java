package org.embeddedt.protestversioning.mixin;

import net.minecraft.client.Minecraft;
import org.embeddedt.protestversioning.Protestversioning;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.regex.Matcher;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "getLaunchedVersion", at = @At("RETURN"), cancellable = true)
    private void replaceLaunchedVersion(CallbackInfoReturnable<String> cir) {
        Matcher matcher = Protestversioning.RELEASE_VERSION.matcher(cir.getReturnValue());
        if(matcher.matches()) {
            cir.setReturnValue(matcher.replaceAll(Protestversioning.ACTUAL_GAME_VERSION));
        }
    }
}
