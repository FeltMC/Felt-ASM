package io.github.feltmc.feltasm.asm;

import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;

public class FeltASMBootstrap {
    public static void init(){
        TargetSelector.register(MixinMethodHandlerSelector.class, "FeltASM");
    }
}
