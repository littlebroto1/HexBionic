package io.github.littlebroto1.hexbionics.entity;

import com.mojang.authlib.GameProfile;
import dev.architectury.extensions.network.EntitySpawnExtension;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public interface IPlayerHusk extends EntitySpawnExtension {
    UUID getTargetUUID();
    void setTargetUUID(UUID uuid);

    String getTargetName();
    void setTargetName(String name);

    void setGameProfile(GameProfile profile);

    @Override
    default void loadAdditionalSpawnData(PacketByteBuf buf) {
        setTargetUUID(buf.readUuid());
        setTargetName(buf.readString());
        setGameProfile(new GameProfile(getTargetUUID(), getTargetName()));
    };

    @Override
    default void saveAdditionalSpawnData(PacketByteBuf buf) {
        buf.writeUuid(getTargetUUID());
        buf.writeString(getTargetName());
    }
}
