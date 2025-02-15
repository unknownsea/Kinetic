package net.seasonal.kinetic.event;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.seasonal.kinetic.Kinetic;

public class FallEffectHandler {
    public static void applyFallEffects(PlayerEntity player, float fallDistance, World world) {
        if (fallDistance >= 3 && fallDistance <= 6 && player.isSprinting()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 5, 1, false, false, true));
            player.sendMessage(Text.literal("§aYou gained momentum!"), true);
        }
        if (fallDistance >= 7 && fallDistance <= 12) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 25, 1, false, false, true));
            player.sendMessage(Text.literal("§eYou sprung an ankle!"), true);
        }
        if (fallDistance > 12) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 50, 1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 50, 1, false, false, true));
            player.sendMessage(Text.literal("§cYou broke your legs!"), true);
        }

        playFallSound(player, fallDistance, world);
    }

    private static void playFallSound(PlayerEntity player, float fallDistance, World world) {
        BlockPos blockPos = player.getBlockPos();
        if (fallDistance > 12) {
            world.playSound(null, blockPos, SoundEvents.ENTITY_SKELETON_HURT, SoundCategory.PLAYERS, 1.0F, 0.5F);
            Kinetic.LOGGER.info("Played ENTITY_SKELETON_HURT");
        } else if (fallDistance >= 7) {
            world.playSound(null, blockPos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            Kinetic.LOGGER.info("Played ENTITY_ZOMBIE_BREAK_WOODEN_DOOR");
        } else if (fallDistance > 3) {
            world.playSound(null, blockPos, SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            world.playSound(null, blockPos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 0.5F, 1.0F);
            Kinetic.LOGGER.info("Played ENTITY_PLAYER_HURT and BLOCK_GRAVEL_BREAK for sprained ankle");
        }
    }


}
