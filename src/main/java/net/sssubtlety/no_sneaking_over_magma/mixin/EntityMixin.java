package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.sssubtlety.no_sneaking_over_magma.FeatureControl.shouldSneakingProtectFromMagma;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract BlockPos getSteppingPosition();

    @Inject(method = "bypassesSteppingEffects", at = @At("HEAD"), cancellable = true)
    private void no_sneaking_over_magma$preventMagmaBypass(CallbackInfoReturnable<Boolean> cir) {
        if(!shouldSneakingProtectFromMagma() && ((Entity)(Object)this).getWorld().getBlockState(this.getSteppingPosition()).getBlock() instanceof MagmaBlock) {
            cir.setReturnValue(false);
        }
    }
}
