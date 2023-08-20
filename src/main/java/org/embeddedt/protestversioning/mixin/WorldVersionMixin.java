package org.embeddedt.protestversioning.mixin;

import net.minecraft.DetectedVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(DetectedVersion.class)
public class WorldVersionMixin {
    private static final Map<String, String> ACTUAL_VERSION = Collections.synchronizedMap(new HashMap<>());
    private static final Pattern RELEASE_VERSION = Pattern.compile("^1\\.([0-9]+)\\.([0-9]+)$");
    @Inject(method = "getName", at = @At("RETURN"), cancellable = true)
    private void protestVersion(CallbackInfoReturnable<String> cir) {
        String newName = ACTUAL_VERSION.computeIfAbsent(cir.getReturnValue(), name -> {
            Matcher m = RELEASE_VERSION.matcher(name);
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
        if(!newName.equals(cir.getReturnValue()))
            cir.setReturnValue(newName);
    }
}
