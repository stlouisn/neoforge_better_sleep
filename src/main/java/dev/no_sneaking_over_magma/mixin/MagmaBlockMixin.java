package dev.no_sneaking_over_magma.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.MagmaBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Makes magma deal damage even when players are crouching.
 */
@Mixin(MagmaBlock.class)
public class MagmaBlockMixin extends Block {

  public MagmaBlockMixin(Settings settings) {
    super(settings);
  }

  @ModifyExpressionValue(
      method = "onSteppedOn",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/entity/Entity;bypassesSteppingEffects()Z"
      )
  )
  private boolean no_sneaking_over_magma$bypassesSteppingEffects(boolean original) {
    return false;
  }
}