package net.sssubtlety.no_sneaking_over_magma.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.MagmaBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sssubtlety.no_sneaking_over_magma.FeatureControl;
import net.sssubtlety.no_sneaking_over_magma.mixin_helper.DamageSourcesMixinAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.enchantment.Enchantments.FROST_WALKER;
import static net.minecraft.registry.RegistryKeys.ENCHANTMENT;
import static net.sssubtlety.no_sneaking_over_magma.FeatureControl.*;

@Mixin(MagmaBlock.class)
abstract class MagmaBlockMixin {
	@Unique private static final int FIRE_DURATION = 1;

	@ModifyExpressionValue(
		method = "onSteppedOn",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/damage/DamageSources;hotFloor()Lnet/minecraft/entity/damage/DamageSource;"
		)
	)
	private DamageSource passMagmaSourceIfFrostWalkerDoesNotProtect(DamageSource original, World world) {
		return FeatureControl.shouldFrostWalkerProtectFromMagma() ?
			original :
			((DamageSourcesMixinAccessor) world.getDamageSources())
				.no_sneaking_over_magma$getHotFloorDamagePredicateIgnored();
	}

	@Inject(
		method = "onSteppedOn",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
		)
	)
	private void trySetFireToLivingEntities(
		World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci
	) {
		if (
			shouldMagmaSetFireToEntities() && !(
				FeatureControl.shouldFrostWalkerProtectFromMagma() &&
				world.getRegistryManager().get(ENCHANTMENT).getHolder(FROST_WALKER)
					.filter(frostWalker ->
						EnchantmentHelper.getHighestEquippedLevel(frostWalker, (LivingEntity) entity) > 0
					).isPresent()
			)
		) entity.setOnFireFor(FIRE_DURATION);
	}

	@Inject(method = "onSteppedOn", at = @At("HEAD"))
	private void tryDamageNonLivingEntities(
		World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci
	) {
		if (!(entity instanceof LivingEntity) && shouldMagmaDamageNonLivingEntities()) {
			entity.damage(world.getDamageSources().hotFloor(), 1.0F);
			if (shouldMagmaSetFireToEntities()) entity.setOnFireFor(FIRE_DURATION);
		}
	}
}
