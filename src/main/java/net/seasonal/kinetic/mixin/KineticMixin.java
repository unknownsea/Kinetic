package net.seasonal.kinetic.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.seasonal.kinetic.Kinetic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class KineticMixin {
	@Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
	private void modifyFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if (player.isCreative() || player.isSpectator()) return;

		Kinetic.LOGGER.info("Player {} fell {} blocks.", player.getName().getString(), fallDistance);

		float newDamage = calculateFallDamage(player, fallDistance, damageMultiplier);
		applyFallEffects(player, fallDistance);

		if (newDamage > 0) {
			if (player.getWorld() instanceof ServerWorld serverWorld) {
				Kinetic.LOGGER.info("Applying {} damage to player {}.", newDamage, player.getName().getString());
				player.damage(serverWorld, damageSource, newDamage);
			}
		} else {
			Kinetic.LOGGER.info("No fall damage applied to player {}.", player.getName().getString());
		}
		cir.setReturnValue(true);
	}

	private float calculateFallDamage(PlayerEntity player, float fallDistance, float damageMultiplier) {
		if (fallDistance <= 3) return 0;
		float damage = (float) Math.pow(fallDistance - 3, 1.2) * 0.5F * damageMultiplier;
		if (player.isSneaking()) damage *= 0.85F;
		Kinetic.LOGGER.info("Calculated fall damage: {} for fall distance: {}", damage, fallDistance);
		return damage;
	}

	private void applyFallEffects(PlayerEntity player, float fallDistance) {
		if (fallDistance >= 3 && fallDistance <= 7 && player.isSprinting()) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, false, false, true));
		}
		if (fallDistance >= 10 && fallDistance <= 12) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, false, true));
		}
		if (fallDistance > 12) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 1, false, false, true));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1, false, false, true));
		}
		if (fallDistance > 12) {
			player.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1.0F, 1.0F);
		} else if (fallDistance > 3) {
			player.playSound(SoundEvents.ENTITY_PLAYER_HURT, 1.0F, 1.0F);
		}
	}
}
