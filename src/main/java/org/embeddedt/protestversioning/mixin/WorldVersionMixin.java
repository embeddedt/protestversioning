package org.embeddedt.protestversioning.mixin;

import org.embeddedt.protestversioning.Protestversioning;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@Mixin(targets = { "net/minecraft/class_3797" })
public class WorldVersionMixin {
    @Unique
    private static final Map<String, String> ACTUAL_VERSION = Collections.synchronizedMap(new HashMap<>());

    @Inject(method = { "getName()Ljava/lang/String;", "method_48019()Ljava/lang/String;" }, at = @At("RETURN"), cancellable = true, require = 0, remap = false)
    private void protestVersion(CallbackInfoReturnable<String> cir) {
        String newName = ACTUAL_VERSION.computeIfAbsent(cir.getReturnValue(), name -> {
            Matcher m = Protestversioning.RELEASE_VERSION.matcher(name);
            if(m.matches()) {
                int major = Integer.valueOf(m.group(1));
                int minor = Integer.valueOf(m.group(2));
                String baseName = m.group(1) + "." + m.group(2);
                if((major == 16 && minor == 5) || (major == 18 && minor == 2)) {
                    return baseName + " LTS";
                } else
                    return baseName;
            }
            return name;
        });
        if(!newName.equals(cir.getReturnValue())) {
            Protestversioning.ACTUAL_GAME_VERSION = newName;
            cir.setReturnValue(newName);
        }
    }
}
