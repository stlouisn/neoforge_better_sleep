package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.enchantment.EnchantmentHelper.hasFrostWalker;
import static net.sssubtlety.no_sneaking_over_magma.NoSneakingOverMagmaConfig.doesFrostWalkerProtectOnMagma;

@Mixin(MagmaBlock.class)
public abstract class MagmaBlockMixin extends Block {
	public MagmaBlockMixin(Settings settings) {
		super(settings);
		throw new IllegalStateException("MagmaBlockMixin's dummy constructor called. ");
	}

	@Redirect(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasFrostWalker(Lnet/minecraft/entity/LivingEntity;)Z"))
	public boolean ignoreFrostWalker(LivingEntity entity) {
		return doesFrostWalkerProtectOnMagma() && hasFrostWalker(entity);
	}
}
