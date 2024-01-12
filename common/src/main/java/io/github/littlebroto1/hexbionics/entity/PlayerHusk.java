package io.github.littlebroto1.hexbionics.entity;

import com.mojang.authlib.GameProfile;
import dev.architectury.networking.NetworkManager;
import io.github.littlebroto1.hexbionics.mixin.PlayerEntityAccessor;
import io.github.littlebroto1.hexbionics.registry.HexBionicsEntityTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerHusk extends PlayerEntity implements IPlayerHusk {
    @Environment(EnvType.CLIENT)
    private PlayerListEntry playerListEntry;

    private UUID targetPlayerUUID;
    private String targetPlayerName;

    public PlayerHusk(ServerWorld world, BlockPos pos, GameProfile profile) {
        super(world, pos, 0.0f, profile, null);
        this.targetPlayerUUID = getUuid();
        this.targetPlayerName = getGameProfile().getName();
        this.setUuid(UUID.randomUUID());
    }

    private PlayerHusk(EntityType<PlayerEntity> type, World world) {
        super(world, world.getSpawnPos(), world.getSpawnAngle(), new GameProfile(UUID.randomUUID(), "Dev_"), null);
        this.targetPlayerUUID = getUuid();
        this.targetPlayerName = getGameProfile().getName();
        this.setUuid(UUID.randomUUID());
    }

    public PlayerHusk(ServerPlayerEntity player) {
        this(player.getWorld(), player.getBlockPos(), player.getGameProfile());

        this.dataTracker.writeUpdatedEntries(player.getDataTracker().getAllEntries());

        this.copyPositionAndRotation(player);

        this.getInventory().clone(player.getInventory());
        //player.getInventory().clear(); TODO: Enable this outside of dev

        player.getActiveStatusEffects().forEach((statusEffect, statusEffectInstance) -> this.addStatusEffect(statusEffectInstance));
        this.setHealth(player.getHealth());
    }

    public static PlayerHusk of(EntityType<PlayerEntity> type, World world) {
        return new PlayerHusk(type, world);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public EntityType<?> getType() {
        return HexBionicsEntityTypeRegistry.PLAYER_HUSK.get();
    }

    @Override
    public String getEntityName() {
        return this.targetPlayerName;
    }

    @Override
    public Text getName() {
        return Text.of(getEntityName());
    }

    @Override
    public boolean shouldSave() {
        return true;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    protected void dropInventory() {
        this.vanishCursedItems();
        this.getInventory().dropAll();
    }

    @Nullable
    @Override
    public ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
        ItemEntity itemEntity = super.dropItem(stack, throwRandomly, retainOwnership);
        if (itemEntity == null) {
            return null;
        } else {
            this.world.spawnEntity(itemEntity);
            return itemEntity;
        }
    }

    /**
     * Override to prevent the loss of UUID when copying
     * @param original
     */
    @Override
    public void copyFrom(Entity original) {
        UUID old = getUuid();
        NbtCompound abilitiesTag = new NbtCompound();
        this.getAbilities().writeNbt(abilitiesTag);

        super.copyFrom(original);

        setUuid(old);
        this.getAbilities().readNbt(abilitiesTag);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("TargetUUID"))
            this.targetPlayerUUID = nbt.getUuid("TargetUUID");
        if (nbt.contains("TargetName"))
            this.targetPlayerName = nbt.getString("TargetName");
        if (nbt.contains("PLAYER_MODEL_PARTS"))
            getDataTracker().set(PLAYER_MODEL_PARTS, nbt.getByte("PLAYER_MODEL_PARTS"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putUuid("TargetUUID", this.targetPlayerUUID);
        nbt.putString("TargetName", this.targetPlayerName);
        nbt.putByte("PLAYER_MODEL_PARTS", getDataTracker().get(PLAYER_MODEL_PARTS));
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public UUID getTargetUUID() {
        return targetPlayerUUID;
    }

    @Override
    public void setTargetUUID(UUID uuid) {
        targetPlayerUUID = uuid;
    }

    @Override
    public String getTargetName() {
        return targetPlayerName;
    }

    @Override
    public void setTargetName(String name) {
        targetPlayerName = name;
    }

    @Override
    public void setGameProfile(GameProfile profile) {
        ((PlayerEntityAccessor)this).hex$setGameProfile(profile);
    }
}
