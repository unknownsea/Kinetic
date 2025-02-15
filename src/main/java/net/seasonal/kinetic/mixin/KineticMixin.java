package net.seasonal.kinetic.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.seasonal.kinetic.event.FallDamageHandler;
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

		FallDamageHandler.handleFall(player, fallDistance, damageMultiplier, damageSource);
		cir.setReturnValue(true);
	}
}
