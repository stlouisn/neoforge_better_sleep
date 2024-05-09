package net.sssubtlety.no_sneaking_over_magma.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.sssubtlety.no_sneaking_over_magma.FeatureControl.*;

@Mixin(MagmaBlock.class)
abstract class MagmaBlockMixin extends Block {
	@Unique private static final int FIRE_DURATION = 1;

	private MagmaBlockMixin() {
        //noinspection DataFlowIssue
        super(null);
		throw new IllegalStateException("MagmaBlockMixin's dummy constructor called!");
	}

	@ModifyExpressionValue(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasFrostWalker(Lnet/minecraft/entity/LivingEntity;)Z"))
	private boolean tryIgnoreFrostWalker(boolean original) {
		return shouldFrostWalkerProtectFromMagma() && original;
	}

	@Inject(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private void trySetFireToLivingEntities(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
		if (shouldMagmaSetFireToEntities()) entity.setOnFireFor(FIRE_DURATION);
	}

	@Inject(method = "onSteppedOn", at = @At("HEAD"))
	private void tryDamageNonLivingEntities(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
		if (!(entity instanceof LivingEntity) && shouldMagmaDamageNonLivingEntities()) {
			entity.damage(world.getDamageSources().hotFloor(), 1.0F);
			if (shouldMagmaSetFireToEntities()) entity.setOnFireFor(FIRE_DURATION);
		}
	}
}
