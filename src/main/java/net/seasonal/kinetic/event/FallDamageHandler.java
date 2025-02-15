package net.seasonal.kinetic.event;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.seasonal.kinetic.Kinetic;

public class FallDamageHandler {
    public static void handleFall(PlayerEntity player, float fallDistance, float damageMultiplier, DamageSource damageSource) {
        float newDamage = calculateFallDamage(fallDistance, damageMultiplier);
        FallEffectHandler.applyFallEffects(player, fallDistance, player.getWorld());

        if (newDamage > 0) {
            if (player.getWorld() instanceof ServerWorld serverWorld) {
                Kinetic.LOGGER.info("Applying {} damage to player {}.", newDamage, player.getName().getString());
                player.damage(serverWorld, damageSource, newDamage);
            }
        } else {
            Kinetic.LOGGER.info("No fall damage applied to player {}.", player.getName().getString());
        }
    }

    private static float calculateFallDamage(float fallDistance, float damageMultiplier) {
        if (fallDistance <= 3) return 0;
        return (float) Math.pow(fallDistance - 3, 1.2) * 0.5F * damageMultiplier;
    }
}
