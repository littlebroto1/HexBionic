package io.github.littlebroto1.hexbionics.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.littlebroto1.hexbionics.HexBionics;
import io.github.littlebroto1.hexbionics.entity.ClientPlayerHusk;
import io.github.littlebroto1.hexbionics.entity.PlayerHusk;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;

public class HexBionicsEntityTypeRegistry {
    private static final EntityType.EntityFactory<PlayerEntity> PLAYER_HUSK_FACTORY = ((type, world) -> (world instanceof ClientWorld clientWorld) ? new ClientPlayerHusk(type, clientWorld) : PlayerHusk.of(type, world));
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(HexBionics.MOD_ID, Registry.ENTITY_TYPE_KEY);

    public static void init() {
        ENTITY_TYPES.register();
    }

    // During the loading phase, refrain from accessing suppliers' entity types (e.g. EXAMPLE_ITEM.get()), they will not be available
    public static final RegistrySupplier<EntityType<PlayerEntity>> PLAYER_HUSK = ENTITY_TYPES.register("player_husk", () -> EntityType.Builder.create(PLAYER_HUSK_FACTORY, SpawnGroup.MISC).setDimensions(0.6F, 1.8F).maxTrackingRange(32).trackingTickInterval(2).build("player_husk"));
}
