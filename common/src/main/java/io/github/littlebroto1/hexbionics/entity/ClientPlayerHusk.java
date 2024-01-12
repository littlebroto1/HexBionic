package io.github.littlebroto1.hexbionics.entity;

import com.mojang.authlib.GameProfile;
import io.github.littlebroto1.hexbionics.mixin.PlayerEntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class ClientPlayerHusk extends AbstractClientPlayerEntity implements IPlayerHusk {

    private UUID targetUUID;
    private String targetName;

    public ClientPlayerHusk(EntityType<PlayerEntity> type, ClientWorld world) {
        super(world, new GameProfile(null, "Hi"), null);
    }

    @Override
    public Identifier getSkinTexture() {
        return MinecraftClient.getInstance().getSkinProvider().loadSkin(this.getGameProfile());
    }

    @Override
    public String getModel() {
        PlayerListEntry playerListEntry = this.getPlayerListEntry();
        //return playerListEntry == null ? DefaultSkinHelper.getModel(getTargetUUID()) : playerListEntry.getModel();
        return "golem";
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public UUID getTargetUUID() {
        return targetUUID;
    }

    @Override
    public void setTargetUUID(UUID uuid) {
        this.targetUUID = uuid;
    }

    @Override
    public String getTargetName() {
        return targetName;
    }

    @Override
    public void setTargetName(String name) {
        this.targetName = name;
    }

    @Override
    public void setGameProfile(GameProfile profile) {
        ((PlayerEntityAccessor)this).hex$setGameProfile(profile);
        MinecraftClient.getInstance().getSkinProvider().loadSkin(getGameProfile(), null, true);
    }
}
