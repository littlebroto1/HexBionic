package io.github.littlebroto1.hexbionics.mixin;

import com.google.common.collect.ImmutableMap;
import io.github.littlebroto1.hexbionics.client.AmethystGolemRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {
    @Inject(method = "reloadPlayerRenderers", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private static void includeGolemRenderer(EntityRendererFactory.Context ctx, CallbackInfoReturnable<Map<String, EntityRenderer<? extends PlayerEntity>>> cir, ImmutableMap.Builder<String, EntityRenderer<? extends PlayerEntity>> builder) {
        builder.put("golem", new AmethystGolemRenderer(ctx));
        cir.setReturnValue(builder.build());
    }
}
