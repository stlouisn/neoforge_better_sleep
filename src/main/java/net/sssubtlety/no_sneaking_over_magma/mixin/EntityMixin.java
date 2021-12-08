package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.sssubtlety.no_sneaking_over_magma.NoSneakingOverMagmaConfig.doesSneakingProtectOnMagma;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract BlockPos getLandingPos();

    @Inject(method = "bypassesSteppingEffects", at = @At("HEAD"), cancellable = true)
    private void preventMagmaBypass(CallbackInfoReturnable<Boolean> cir) {
        if(!doesSneakingProtectOnMagma() && ((Entity)(Object)this).world.getBlockState(this.getLandingPos()).getBlock() instanceof MagmaBlock) {
            cir.setReturnValue(false);
        }
    }
}
