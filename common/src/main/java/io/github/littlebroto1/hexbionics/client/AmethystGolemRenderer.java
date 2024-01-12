package io.github.littlebroto1.hexbionics.client;

import io.github.littlebroto1.hexbionics.HexBionics;
import io.github.littlebroto1.hexbionics.HexBionicsClient;
import io.github.littlebroto1.hexbionics.entity.ReplacedPlayerEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

import java.util.Optional;

public class AmethystGolemRenderer extends GeoReplacedEntityRenderer<ReplacedPlayerEntity> {
    public AmethystGolemRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AmethystGolemModel<>(), new ReplacedPlayerEntity());
    }

    public void renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
        Optional<GeoBone> bone = getGeoModelProvider().getModel(getGeoModelProvider().getModelResource(this.animatable)).getBone("left_arm");
        if (bone.isPresent()) {
            renderRecursively(bone.get(), matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, 0, 255.0f,255.0f,255.0f, 255.0f);
        }
    }

    public void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
        Optional<GeoBone> bone = getGeoModelProvider().getModel(getGeoModelProvider().getModelResource(this.animatable)).getBone("right_arm");
        if (bone.isPresent()) {
            renderRecursively(bone.get(), matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, 0, 255.0f,255.0f,255.0f, 255.0f);
        }
    }

    public void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve) {
        arm.pitch = 0.0f;
        arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
        sleeve.pitch = 0.0f;
        sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
    }

    /*@Override
    public Identifier getTexture(PlayerEntity entity) {
        return new Identifier(HexBionics.MOD_ID, "textures/entity/player/amethyst_golem.png");
    }*/
}
