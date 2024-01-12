package io.github.littlebroto1.hexbionics.mixin;

import io.github.littlebroto1.hexbionics.BionicsUtility;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "getActiveEyeHeight", at = @At("RETURN"), cancellable = true)
    public void getGolemEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        if (!BionicsUtility.isGolem((PlayerEntity)(Object)this)) {
            return;
        }
        cir.setReturnValue(cir.getReturnValueF() * 0.4f);
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    public void getGolemDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (!BionicsUtility.isGolem((PlayerEntity)(Object)this)) {
            return;
        }
        cir.setReturnValue(cir.getReturnValue().scaled(1.0f, 0.4f));
    }
}
