package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.enchantment.EnchantmentHelper.hasFrostWalker;
import static net.sssubtlety.no_sneaking_over_magma.NoSneakingOverMagmaConfig.doesFrostWalkerProtectOnMagma;

@Mixin(MagmaBlock.class)
public abstract class MagmaBlockMixin extends Block {
	public MagmaBlockMixin(Settings settings) {
		super(settings);
		throw new IllegalStateException("MagmaBlockMixin's dummy constructor called!");
	}

//	@Redirect(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasFrostWalker(Lnet/minecraft/entity/LivingEntity;)Z"))
//	public boolean ignoreFrostWalker(LivingEntity entity) {
//		return doesFrostWalkerProtectOnMagma() && hasFrostWalker(entity);
//	}

	@Inject(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasFrostWalker(Lnet/minecraft/entity/LivingEntity;)Z"))
	public void ignoreFrostWalker(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
		if (!doesFrostWalkerProtectOnMagma() && hasFrostWalker((LivingEntity)entity))
			entity.damage(DamageSource.HOT_FLOOR, 1.0F);
	}


}
