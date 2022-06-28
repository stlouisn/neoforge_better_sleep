package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sssubtlety.no_sneaking_over_magma.DummyLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.enchantment.EnchantmentHelper.hasFrostWalker;
import static net.sssubtlety.no_sneaking_over_magma.FeatureControl.*;
import static org.objectweb.asm.Opcodes.IFNE;

@Mixin(MagmaBlock.class)
public abstract class MagmaBlockMixin extends Block {
	private static final int FIRE_DURATION = 1;
	private static DummyLivingEntity DUMMY_LIVING_ENTITY = null;

	private MagmaBlockMixin(Settings settings) {
		super(settings);
		throw new IllegalStateException("MagmaBlockMixin's dummy constructor called!");
	}

//	@Redirect(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasFrostWalker(Lnet/minecraft/entity/LivingEntity;)Z"))
//	public boolean ignoreFrostWalker(LivingEntity entity) {
//		return doesFrostWalkerProtectOnMagma() && hasFrostWalker(entity);
//	}

	@ModifyArg(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasFrostWalker(Lnet/minecraft/entity/LivingEntity;)Z"))
	private LivingEntity no_sneaking_over_magma$tryIgnoreFrostWalker(LivingEntity entity) {
		if (!shouldFrostWalkerProtectFromMagma() && hasFrostWalker(entity)) {
			return no_sneaking_over_magma$getDummyLivingEntity(entity);
		} else return entity;
	}

	@Inject(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private void no_sneaking_over_magma$trySetFire(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
		if (shouldMagmaSetFireToEntities()) entity.setOnFireFor(FIRE_DURATION);
	}

	@Inject(method = "onSteppedOn", at = @At(
			value = "JUMP",
			opcode = IFNE,
			ordinal = 0,
			shift = At.Shift.AFTER
	))
	private void no_sneaking_over_magma$tryIgnoreFrostWalkerAndSetFire(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
		if (!(entity instanceof LivingEntity) && shouldMagmaDamageNonLivingEntities()) {
			entity.damage(DamageSource.HOT_FLOOR, 1.0F);
			if (shouldMagmaSetFireToEntities()) entity.setOnFireFor(FIRE_DURATION);
		}
	}

	private LivingEntity no_sneaking_over_magma$getDummyLivingEntity(LivingEntity livingEntity) {
		if (DUMMY_LIVING_ENTITY == null) DUMMY_LIVING_ENTITY = new DummyLivingEntity(livingEntity.world);
		else DUMMY_LIVING_ENTITY.world = livingEntity.world;

		return DUMMY_LIVING_ENTITY;
	}
}
