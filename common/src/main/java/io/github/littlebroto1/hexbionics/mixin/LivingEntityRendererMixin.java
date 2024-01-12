package io.github.littlebroto1.hexbionics.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    public <T extends LivingEntity> void hideIfNameHidden(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (!livingEntity.shouldRenderName()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
