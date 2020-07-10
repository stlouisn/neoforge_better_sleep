package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MagmaBlock;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagmaBlock.class)
public abstract class MagmaBlockMixin extends Block {
	public MagmaBlockMixin(Settings settings) {
		super(settings);
		throw new IllegalStateException("MagmaBlockMixin's dummy constructor called. ");
	}
//
//	@Override
//	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
//		if (!entity.isFireImmune()) {
////			entity.setFireTicks(entity.getFireTicks() + 1);
////			if (entity.getFireTicks() == 0) {
////				entity.setOnFireFor(8);
////			}
//			entity.damage(DamageSource.HOT_FLOOR, 1.0F);
//		}
//
//		super.onEntityCollision(state, world, pos, entity);
//	}
//
//	/**
//	 * @author supersaiyansubtlety
//	 * @reason must completely prevent default behavior. This behavior is now in onEntityCollision
//	 */
	@Redirect(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasFrostWalker(Lnet/minecraft/entity/LivingEntity;)Z"))
	public boolean ignoreFrostWalker(LivingEntity entity) {
		return false;
	}
}
