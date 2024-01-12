package io.github.littlebroto1.hexbionics.mixin;

import io.github.littlebroto1.hexbionics.client.AmethystGolemRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {


    @Shadow @Final private EntityRenderDispatcher entityRenderDispatcher;

    @Redirect(method = {"renderArm", "renderArmHoldingItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/render/entity/EntityRenderer;"))
    private <T extends Entity> EntityRenderer<? super T> getRenderer(EntityRenderDispatcher instance, T entity) {
        EntityRenderer<? super T> renderer = instance.getRenderer(entity);

        if (renderer instanceof AmethystGolemRenderer) {
            return (EntityRenderer<? super T>) ((EntityRenderDispatcherAccessor)instance).getModelRenderers().get("default");
        }
        else {
            return renderer;
        }
    }

    @Redirect(method = {"renderArm", "renderArmHoldingItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderRightArm(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;)V"))
    private void replaceRightArm(PlayerEntityRenderer instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
        EntityRenderer<? super PlayerEntity> renderer = this.entityRenderDispatcher.getRenderer(player);
        if (renderer instanceof AmethystGolemRenderer golemRenderer) {
            golemRenderer.renderRightArm(matrices, vertexConsumers, light, player);
        }
        else {
            instance.renderRightArm(matrices, vertexConsumers, light, player);
        }
    }

    @Redirect(method = {"renderArm", "renderArmHoldingItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderLeftArm(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;)V"))
    private void replaceLeftArm(PlayerEntityRenderer instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
        EntityRenderer<? super PlayerEntity> renderer = this.entityRenderDispatcher.getRenderer(player);
        if (renderer instanceof AmethystGolemRenderer golemRenderer) {
            golemRenderer.renderLeftArm(matrices, vertexConsumers, light, player);
        }
        else {
            instance.renderLeftArm(matrices, vertexConsumers, light, player);
        }
    }
}
